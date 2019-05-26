<template>
   <div>
       <el-col :span="24">
           <ibox name="ibox">
               <div slot="content" style="width:100%" >
                        <div class="title_top_all"><strong>我的申请</strong></div>
                        <div class="hr-line-solid-blue"></div>
                        <el-row>
                            <el-form :inline="true" class="demo-form-inline">
                                <el-row>
                                    <el-form-item label="申请日期:" prop="time">
                                            <div class="block">
                                                <el-date-picker
                                                v-model="searchMyApplyForm.time"
                                                type="date"
                                                placeholder="请选择申请日期"
                                                value-format="yyyy-MM-dd"
                                                :picker-options="pickerOptions">
                                                </el-date-picker>
                                            </div>
                                    </el-form-item>
                                    <el-form-item>
                                            <el-button type="primary" icon="el-icon-search" @click="ruleDataLoad" round >查询</el-button>
                                    </el-form-item>
                                </el-row>
                                <el-row>
                                    <el-table v-loading="loading"
                                        ref="myApplyDataTable" :data="myApplyData"    @current-change="currentChange"   handleLoad="true" border highlight-current-row max-height="450" >
                                        <el-table-column type="selection" width="60"></el-table-column>
                                            <el-table-column prop="workid" label="流程号" width="110" show-overflow-tooltip></el-table-column>
                                             <el-table-column prop="productcategories" label="图书上架分类" width="210" show-overflow-tooltip> </el-table-column>
                                            <el-table-column prop="apply_time" label="申请时间" width="250" show-overflow-tooltip></el-table-column>
                                            <el-table-column prop="audit" label="审核状态" width="210" show-overflow-tooltip> </el-table-column>
                                            <el-table-column prop="model_cn_name" label="信息来源" width="210" show-overflow-tooltip> </el-table-column>
                                            <el-table-column  label="操作" width="69" >
                                            <template slot-scope="scope">
                                                <el-button type="warning" icon="el-icon-view" title="详情" @click="detail(scope.row)" :disabled="scope.row.auditState != '01'" circle></el-button>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                    <el-pagination @size-change="handleSizeChange"  @current-change="handleCurrentChange" :current-page="cur_page"
                                                    :page-sizes="[10,15,20,25]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper"
                                                    :total="totalCount">
                                    </el-pagination>  
                                </el-row>

                            </el-form>
                        </el-row>
               </div>
           </ibox>
       </el-col>

       <audit-detail ref="audit"></audit-detail>

       
   </div>
</template>


<script>
import auditDetail from "./detail";
export default {
    components: {
        'audit-detail': auditDetail
    },
    data() {
        return {
            searchMyApplyForm:{},
            myApplyData:[],
            currentRow : {},
            loading: false,
            pagesize: 10,//默认每页数据量为10
            cur_page:1,//当前页,默认第一页
            totalCount:20,//默认数据总数
            pickerOptions: {//时间选择器控件
                disabledDate(time) {
                    return time.getTime() > Date.now();
                },
                shortcuts: [{
                    text: '今天',
                    onClick(picker) {
                    picker.$emit('pick', new Date());
                    }
                }, {
                    text: '昨天',
                    onClick(picker) {
                    const date = new Date();
                    date.setTime(date.getTime() - 3600 * 1000 * 24);
                    picker.$emit('pick', date);
                    }
                }, {
                    text: '一周前',
                    onClick(picker) {
                    const date = new Date();
                    date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', date);
                    }
                }]
                }


        }
    },
    methods: {
        ruleDataLoad: function() {
            this.$axios.get("http://localhost:8088/workflow/myapply", {params: {
            'pagesize': this.pagesize,
            'cur_page': this.cur_page,
            'id': sessionStorage.getItem("id"),
            'applyTime': this.searchMyApplyForm.time
            }}).then((response) => {
                this.myApplyData = response.data.userApply;
                this.totalCount = response.data.total;
            })

        },
        handleSizeChange(val) {
            this.pagesize = val;//每页显示数据数量
            this.dataLoad(this.cur_page,this.pagesize);
        },
        handleCurrentChange(val) {
            this.cur_page = val;//显示不同页的数据
            this.dataLoad(this.cur_page,this.pagesize);
        },
        dataLoad: function(cur_page,pagesize) {
            this.$axios.get("http://localhost:8088/workflow/myapply",{params: {
            'pagesize': this.pagesize,
            'cur_page': this.cur_page,
            'id': sessionStorage.getItem("id")
            }}).then((response) =>{
           
            this.myApplyData = response.data.userApply;
            this.totalCount = response.data.total;
            
        });
        },
        currentChange(row) {
            this.currentRow = row;
        },
        detail(row) {
            this.$refs.audit.open(JSON.stringify(row));
        }

    },
    created() {
        if (sessionStorage.getItem("id") && sessionStorage.getItem("token")) {
            this.$store.dispatch('setNewName',sessionStorage.getItem("name"));
            this.$axios.get("http://localhost:8088/workflow/myapply", {params: {
            'pagesize': this.pagesize,
            'cur_page': this.cur_page,
            'id': sessionStorage.getItem("id")
        }}).then((response) => {
            this.myApplyData = response.data.userApply;
            this.totalCount = response.data.total;
        })

        }else {
            this.$message({
                showClose: true,
                message: '请先登录!',
                type: 'error',
                duration: 2500
                });
            this.$router.push({path: '/login'})
        }

    }
    
}
</script>


<style>
    .hr-line-solid-blue {
        border-top: 1px solid #ff7920;
        color: #ffffff;
        background-color: #ffffff;
        height: 1px;
        margin-bottom: 20px;
       
    }
    .title_top_all {
        height: 22px;
        padding-left: 10px;
        font-size: 16px;
        color: #ff7920;
        line-height: 22px;
        margin-bottom: 10px;
        border-left: 12px ;
        
    }
</style>

