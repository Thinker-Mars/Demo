<template>
  <div>
      <el-col :span="24">


          <ibox name="ibox">

              <div class="title_top_all"><strong>资产总览</strong></div>
              <div class="hr-line-solid-blue"></div>
              <div id="bookproperty" :style="{width: '1100px', height: '500px'}"></div>


          </ibox>



      </el-col>


  </div> 
</template>

<script>
export default {
    name: '资产总览',
    data() {
        return {
            data: {},
            bookNameList: [],
            bookCountList: [],
            bookinfoList: []

        };

    },
    // mounted() {
    //     this.drawLine();
    // },
    methods: {
        drawLine() {
            //this.getBookCate();
            let bookproperty = this.$echarts.init(document.getElementById('bookproperty'));
             bookproperty.setOption({
                title : {
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    type: 'scroll',//可滚动翻页的图例
                    orient: 'vertical',//图例列表的布局朝向
                    right: 10,//图例组件离容器右侧的距离
                    top: 20,//图例组件离容器上侧的距离
                    bottom: 20,//图例组件离容器下侧的距离
                    data: this.data.legendData,//图例的数据数组
                    selected: this.data.selected//图例选中状态表
                },
                series : [
                    {
                    type: 'pie',//饼图
                    radius : '85%',//饼图的半径
                    center: ['40%', '50%'],//扇形面积
                    data: this.data.seriesData,//列中的数据内容
                        itemStyle: {//图形的颜色
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
             })

        },
        // async getdata() {
        //         await this.getBookCate();
        //         console.log(this.bookNameList);
        //         var legendData = [];
        //         var seriesData = [];
        //         var selected = {};
        //         for (var i = 0; i < this.bookNameList.length; i++) {
        //             legendData.push(this.bookNameList[i]);
        //             seriesData.push({
        //                 name: this.bookNameList[i],//种类图书名
        //                 value: Math.round(Math.random() * 100000)//每个种类图书的数量
        //             });
        //             selected[this.bookNameList[i]] = i < 4;//默认显示4个种类的图书的饼图
        //         }
        //         this.data.legendData = legendData;
        //         this.data.seriesData = seriesData;
        //         this.data.selected = selected;
                

        // },
        getBookCate: function() {
            this.$axios.get("http://localhost:8088/chart/bookcate",{params: {
                }}).then((response) => {
                    this.bookinfoList = response.data;
                    for(let i = 0; i < this.bookinfoList.length; i++) {
                        this.bookNameList.push(this.bookinfoList[i]["productcategories"]);
                        this.bookCountList.push(this.bookinfoList[i]["count"]);
                    }
                    var legendData = [];
                    var seriesData = [];
                    var selected = {};
                    for (var i = 0; i < this.bookNameList.length; i++) {
                    legendData.push(this.bookNameList[i]);
                    seriesData.push({
                        name: this.bookNameList[i],//种类图书名
                        value: Math.round(this.bookCountList[i])//每个种类图书的数量
                    });
                        selected[this.bookNameList[i]] = i < 4;//默认显示4个种类的图书的饼图
                    }
                    this.data.legendData = legendData;
                    this.data.seriesData = seriesData;
                    this.data.selected = selected;
                    this.drawLine();
                })
                

        }

    },
    created() {
        if (sessionStorage.getItem("id") && sessionStorage.getItem("token")) {
            this.$store.dispatch('setNewName',sessionStorage.getItem("name"));
            this.getBookCate();

        }
        else {
            this.$message({
                showClose: true,
                message: '请先登录!',
                type: 'error',
                duration: 2500
                });
            this.$router.push({path: '/login'})
        }
        
        // this.$axios.get("http://localhost:8088/chart/bookcate",{params: {
        // }}).then((response) => {
        //     this.bookinfoList = response.data;
        //     for(let i = 0; i < this.bookinfoList.length; i++) {
        //         this.bookNameList.push(this.bookinfoList[i]["productcategories"]);
        //     }
        // })


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




