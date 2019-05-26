<template>
    <div>
        <el-col :span="24">
            <ibox name="ibox">
                     <div slot="content" style="width:100%" >
                        <div class="title_top_all"><strong>元数据采集</strong></div>
                        <div class="hr-line-solid-blue"></div>
                        <el-row>
                                <el-form :inline="true" class="demo-form-inline">
                                    <el-row>
                                        <el-form-item >    
                                            <el-select clearable v-model="searchForm.productCategories" placeholder="请选择采集类型">
                                                <el-option v-for="item in productitem" :key="item.productId" :label="item.productCategories" :value="item.productId"> </el-option>
                                            </el-select>
                                        </el-form-item>

                                        <el-form-item>
                                            <el-button type="primary" icon="el-icon-search" @click="dataLoad" round >查询</el-button>
                                        </el-form-item>

                                    </el-row>
                                </el-form>
                        </el-row>
                        <el-row>
                            <el-table v-loading="loading" element-loading-text="数据采集中,请稍候"  
                             ref="dataCollectTable" :data="bookMetaCollectData"    @current-change="currentChange"   handleLoad="true" border highlight-current-row max-height="450" >
                            <el-table-column type="selection" width="50"></el-table-column>
                                <el-table-column prop="taskName" label="采集类型" width="150" show-overflow-tooltip></el-table-column>
                                <el-table-column prop="lastTime" label="最后一次运行时间" width="220" show-overflow-tooltip></el-table-column>
                                <el-table-column prop="result" label="最后一次运行结果" width="165" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="workerName" label="最后一次执行人" width="165" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="runTime" label="运行耗时" width="220" show-overflow-tooltip> </el-table-column>
                                <el-table-column  label="操作" width="148" >
                                <template slot-scope="scope">
                                    <el-button type="warning" icon="el-icon-refresh" title="执行" @click="execute(scope.row)" circle></el-button>
                                    <el-button type="danger" icon="el-icon-bell" title="执行结果" @click="detail(scope.row)" :disabled="scope.row.lastTime == null" circle></el-button>
                                </template>
                            </el-table-column>
                            </el-table>
                            <el-pagination @size-change="handleSizeChange"  @current-change="handleCurrentChange" :current-page="cur_page"
                                            :page-sizes="[10,15,20,25]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper"
                                            :total="totalCount">
                            </el-pagination>            
                        </el-row>
                    </div>
            </ibox>
        </el-col>

        
    </div>
</template>

<script>

    export default {

        data() {
            return {
                searchForm : {
                },
                bookMetaCollectData:[],
                productitem:[],
                currentRow : {},
                pagesize: 10,//默认每页数据量为10
                cur_page:1,//当前页,默认第一页
                totalCount:300,//默认数据总数
                loading:false
            };
        },
        methods: {
            dataLoad: function(cur_page,pagesize) {
                this.$axios.get("http://localhost:8088/collect/task",{params: {
                'pagesize': this.pagesize,
                'cur_page': this.cur_page,
                'taskName': this.searchForm.productCategories
                }}).then((response) =>{
                //console.log(response.data)
                this.bookMetaCollectData = response.data.task;
                this.totalCount = response.data.total;
                //this.searchForm = {};
                
            });
            },
            currentChange(row) {
                this.currentRow = row;
            },
            handleSizeChange(val) {
                this.pagesize = val;//每页显示数据数量
                this.dataLoad(this.cur_page,this.pagesize);
            },
            handleCurrentChange(val) {
                this.cur_page = val;//显示不同页的数据
                this.dataLoad(this.cur_page,this.pagesize);
            },
            execute(row) {
                var task = {};
                task.taskName = row.taskName;
                task.workerId = sessionStorage.getItem("id");
                this.$confirm('确定执行 '+ row.taskName +' 数据采集任务?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.loading = true;
                    this.$axios.post("http://localhost:8088/collect/execute",{taskName: task})
                    .then((response) => {
                        if (response.data.success == 200) {
                            this.$notify({
                                    title: '',
                                    message: '采集成功!',
                                    type: 'success',
                                    duration: 2500
                                    });

                        }else {
                            this.$notify.error({
                                    title: '',
                                    message: '采集失败!',
                                    duration: 2500
                                    });
                        }
                        this.dataLoad(this.cur_page,this.pagesize);//刷新页面
                        this.loading = false;

                })

                })
            },
            detail(row) {
                 this.$alert('<i>成功采集' + row.count +'条数据<i>', {
                    dangerouslyUseHTMLString: true
                });
            }



        },
        computed:{

        },
        created() {
            if (sessionStorage.getItem("id") && sessionStorage.getItem("token")) {
                this.$store.dispatch('setNewName',sessionStorage.getItem("name"));
                this.loading = true;

                this.$axios.get("http://localhost:8088/bookinfo/bookid",{param: {orderBy: "1"}}).then((response) =>{
                    this.productitem = response.data;
                });

                this.$axios.get("http://localhost:8088/collect/task",{params: {
                    'pagesize': this.pagesize,
                    'cur_page': this.cur_page
                    }}).then((response) =>{
                    this.bookMetaCollectData = response.data.task;
                    this.totalCount = response.data.total;
                    this.loading = false;
                });

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


        },
        watch: {


        },
        mounted() {

        }
    };
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
        border-top: 1px solid #ff8c20;
        color: #ffffff;
        background-color: #ffffff;
        height: 1px;
        margin-bottom: 20px;
    }

    .title_top_all {
        height: 22px;
        padding-left: 10px;
        font-size: 16px;
        color: #ff9e20;
        line-height: 22px;
        margin-bottom: 10px;
        border-left: 12px ;
    }

</style>

