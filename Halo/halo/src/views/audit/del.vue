<template>
   <div>
       <el-col :span="24">
           <ibox name="ibox">
               <div slot="content" style="width:100%" >
                        <div class="title_top_all"><strong>删除图书信息审核</strong></div>
                        <div class="hr-line-solid-blue"></div>
                        <el-row>
                            <el-form :inline="true" class="demo-form-inline">
                                <el-row>
                                    <el-form-item>
                                        <el-input v-model="searchForm.workid" placeholder="请输入流程号"></el-input>
                                    </el-form-item>
                                    <el-form-item>
                                            <el-button type="primary" icon="el-icon-search" @click="dataLoad" round >查询</el-button>
                                    </el-form-item>
                                    <el-form-item>
                                            <el-button type="primary" icon="el-icon-check"  round >批量审批</el-button>
                                    </el-form-item>
                                </el-row>
                                <el-row>
                                    <el-table v-loading="loading"     
                                        ref="bookAuditTable" :data="bookAuditData"    @current-change="currentChange"   handleLoad="true" border highlight-current-row max-height="450" >
                                        <el-table-column type="selection" width="50"></el-table-column>
                                            <el-table-column prop="workid" label="流程号" width="110" show-overflow-tooltip></el-table-column>
                                            <el-table-column prop="name" label="图书名称" width="250" show-overflow-tooltip></el-table-column>
                                            <el-table-column prop="author_tag" label="作者" width="150" show-overflow-tooltip> </el-table-column>
                                            <el-table-column prop="publish_company" label="出版社" width="180" show-overflow-tooltip> </el-table-column>
                                            <el-table-column prop="star_level" label="评分" width="95"></el-table-column>
                                            <el-table-column prop="price" label="售价" width="85"></el-table-column>
                                            <el-table-column prop="publishtime" label="出版日期" width="200"></el-table-column>
                                            <el-table-column  label="操作" width="60" >
                                            <template slot-scope="scope">
                                                <el-button type="warning" icon="el-icon-check" title="审批" @click="approve(scope.row)"  circle></el-button>
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

       <approve-opinion ref="approve" @search="search" ></approve-opinion>
   </div>
</template>

<script>
import opinion from "./opinion";
export default {
    components: {
        'approve-opinion': opinion

    },
    data() {
        return {
            searchForm: {},
            currentRow : {},
            bookAuditData:[],
            pagesize: 10,//默认每页数据量为10
            cur_page:1,//当前页,默认第一页
            totalCount:20,//默认数据总数
            loading: false

        }
    },
    methods: {
        search: function() {
            this.$axios.get("http://localhost:8088/workflow/bookaudit",{params: {
                'pagesize': this.pagesize,
                'cur_page': this.cur_page,
                'model': "03"
                }}).then((response) =>{
                this.bookAuditData = response.data.bookAudit;
                this.totalCount = response.data.total;
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
        dataLoad: function(cur_page,pagesize) {
            this.$axios.get("http://localhost:8088/workflow/bookaudit",{params: {
            'workid': this.searchForm.workid,
            'pagesize': this.pagesize,
            'cur_page': this.cur_page,
            'model': "03"
            }}).then((response) =>{
            //console.log(response.data)
            this.bookAuditData = response.data.bookAudit;
            this.totalCount = response.data.total;
            this.searchForm = {}
            
        });
        },
        approve(row) {
            this.$refs.approve.open(JSON.stringify(row));
        }

    },
    created() {
        if (sessionStorage.getItem("id") && sessionStorage.getItem("token")) {
            this.$store.dispatch('setNewName',sessionStorage.getItem("name"));
            this.$axios.get("http://localhost:8088/workflow/bookaudit",{params: {
                'pagesize': this.pagesize,
                'cur_page': this.cur_page,
                'model': "03"
                }}).then((response) =>{
                this.bookAuditData = response.data.bookAudit;
                this.totalCount = response.data.total;
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
