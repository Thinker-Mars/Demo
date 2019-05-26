<template>
  <div>
      <el-col :span="24">


          <ibox name="ibox">

              <div class="title_top_all"><strong>周数据活跃度</strong></div>
              <div class="hr-line-solid-blue"></div>
              
              <el-row>
                    <div class="block">
                        <span class="demonstration">动态查询:</span>
                        <el-date-picker
                        v-model="time"
                        type="daterange"
                        align="right"
                        unlink-panels
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="yyyy-MM-dd"
                        :picker-options="pickerOptions">
                        </el-date-picker>
                        <el-button type="warning" round @click="search">搜索</el-button>
                    </div>
                    
              </el-row>
              


              <div id="bookChart" v-loading="loading" :style="{width: '1100px', height: '500px'}"></div>


          </ibox>

      </el-col>


  </div> 
</template>

<script>
export default {
    name: 'weekDataActivity',
    data() {
        return {
            pickerOptions: {
                shortcuts: [{
                    text: '最近一周',
                    onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                    picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                    picker.$emit('pick', [start, end]);
                    }
                }]
            },
            time: [],
            day: '',
            weekDataList: [],//所有检索数据
            addDataList: [],//新增数据量
            deleteDataList: [],//删除数据量
            checkDataList: [],//变更数据量
            dateList: [],//时间数组
            loading: false
        };

    },
    mounted() {
        //this.drawLine();
    },
    methods: {
        search: function() {
            //console.log(this.time);
            //查询前先清空
            this.addDataList = [];
            this.checkDataList = [];
            this.deleteDataList = [];
            //未选择时间段，默认显示近7天数据
            if (this.time == null || this.time.length != 2) {
                var moment = require("moment");
                var day = moment(new Date()).format("YYYY-MM-DD");
                this.$axios.get("http://localhost:8088/chart/gettime",{params: {
                    'endTime': day,
                    'days': 6
                }}).then((response) => {
                    this.dateList = response.data;
                    this.dataSearch(this.dateList[0],6);
                })
                return;
            }
            else {
                let startTime = this.time[0];
                let endTime = this.time[1];
                this.getDays(startTime,endTime);
                if (this.day > 6) {
                    this.$notify({
                        title: '',
                        message: '时间间隔应为7天内!',
                        type: 'warning',
                        duration: 2500
                        });
                        return;
                }
                else {
                    this.getTime(endTime,this.day);
                    this.dataSearch(startTime, this.day);

                }

            }     
        
        },
        dataSearch(startTime, day) {
                this.$axios.get("http://localhost:8088/chart/weekdata",{params: {
                    'startTime': startTime,
                    'day': day
                }}).then((response) => {
                    this.weekDataList = response.data;
                    for (let i = 0; i < this.weekDataList.length; i++) {
                        //遍历取出新增数据量(包含Excel新增)
                        this.addDataList.push(this.weekDataList[i][0]["addCount"] + this.weekDataList[i][0]["excelCount"]);
                        //遍历取出变更数据量
                        this.checkDataList.push(this.weekDataList[i][0]["checkCount"]);
                        //遍历取出删除数据量
                        this.deleteDataList.push(this.weekDataList[i][0]["deleteCount"]);
                    }
                    this.drawLine();
                })


        },
        //取得查询时的时间数组
        //得到时间差
        getDays(date1 , date2){
			var date1Str = date1.split("-");//将日期字符串分隔为数组,数组元素分别为年.月.日
			//根据年 . 月 . 日的值创建Date对象
			var date1Obj = new Date(date1Str[0],(date1Str[1]-1),date1Str[2]);
			var date2Str = date2.split("-");
			var date2Obj = new Date(date2Str[0],(date2Str[1]-1),date2Str[2]);
			var t1 = date1Obj.getTime();
			var t2 = date2Obj.getTime();
			var dateTime = 1000*60*60*24; //每一天的毫秒数
			var minusDays = Math.floor(((t2-t1)/dateTime));//计算出两个日期的天数差
			var days = Math.abs(minusDays);//取绝对值
			this.day = days;
        },
        getTime(endTime, days) {
            this.$axios.get("http://localhost:8088/chart/gettime",{params: {
                'endTime': endTime,
                'days': days
            }}).then((response) => {
                this.dateList = response.data;
                //this.time.push(this.dateList[0], this.dateList[this.dateList.length - 1]);
                //this.search();
                //this.time = [];
            })

        },
        drawLine() {
            let bookChart = this.$echarts.init(document.getElementById('bookChart'));
             bookChart.setOption({
                tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data:['元数据删除','元数据新增','元数据变更']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : this.dateList
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'元数据删除',
                    type:'bar',
                    data:this.deleteDataList
                },
                {
                    name:'元数据新增',
                    type:'bar',
                    data:this.addDataList
                },
                {
                    name:'元数据变更',
                    type:'bar',
                    data:this.checkDataList
                }
            ]
             })

        }

    },
    created() {
        if (sessionStorage.getItem("id") && sessionStorage.getItem("token")) {

            this.$store.dispatch('setNewName',sessionStorage.getItem("name"));
            this.loading = true;
            //格式化时间
            var moment = require("moment");
            var day = moment(new Date()).format("YYYY-MM-DD");
            //默认显示前7天的数据(包含今天)
            //取得时间数组,后调用dataSearch方法查询数据
            this.$axios.get("http://localhost:8088/chart/gettime",{params: {
                'endTime': day,
                'days': 6
            }}).then((response) => {
                this.dateList = response.data;
                this.dataSearch(this.dateList[0],6);
                this.loading = false;
            })

        }
        else {
            this.$message({
                showClose: true,
                message: '请先登录!',
                type: 'error',
                duration: 2500
                });
            this.$router.push({path: '/'})

        }

        
    }
    
}
</script>


<style scoped>
    .hr-line-dashed {
        border-top: 1px dashed #e7eaec;
        color: #ffffff;
        background-color: #ffffff;
        height: 1px;
        margin-bottom: 20px;
    }

    .hr-line-solid-blue {
        border-top: 1px solid #205bff;
        color: #ffffff;
        background-color: #ffffff;
        height: 1px;
        margin-bottom: 20px;
    }

    .title_top_all {
        height: 22px;
        padding-left: 10px;
        font-size: 16px;
        color: #205bff;
        line-height: 22px;
        margin-bottom: 10px;
        border-left: 12px ;
    }
    .datachart{
        position: relative;
        left: 30px;
        top: 20px;
        width: 150px;
        height: 150px;
        margin-right:auto


    }


</style>




