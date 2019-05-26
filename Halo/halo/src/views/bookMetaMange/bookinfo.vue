<template>
    <div>
        <el-col :span="24">
            <ibox name="ibox">
                     <div slot="content" style="width:100%" >
                        <div class="title_top_all"><strong>图书信息管理</strong></div>
                        <div class="hr-line-solid-blue"></div>
                        <el-row>
                                <el-form :inline="true" class="demo-form-inline">
                                    <el-row>
                                        <el-form-item >    
                                            <el-select clearable v-model="searchForm.productCategories" placeholder="请选择图书分类">
                                                <el-option v-for="item in productitem" :key="item.productId" :label="item.productCategories" :value="item.productId"> </el-option>
                                            </el-select>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-input v-model="searchForm.autherName" placeholder="请输入作者姓名"></el-input>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-input v-model="searchForm.bookName" placeholder="请输入书籍名称"></el-input>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-input v-model="searchForm.publishCompany" placeholder="请输入出版社名称"></el-input>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button type="primary" icon="el-icon-search" round @click="dataLoad">查询</el-button>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button type="primary" icon="el-icon-plus" round @click="add">新增</el-button>
                                        </el-form-item>
                                    </el-row>
                                </el-form>
                        </el-row>
                        <el-row>
                            <el-table v-loading="loading" element-loading-text="数据加载中"
                             ref="bookMetaTable" :data="bookMetaData"    @current-change="currentChange"   handleLoad="true" border highlight-current-row max-height="450" >
                            <el-table-column type="selection" width="50"></el-table-column>
                                <el-table-column prop="productCategories" label="图书分类" width="110" show-overflow-tooltip></el-table-column>
                                <el-table-column prop="name" label="图书名称" width="250" show-overflow-tooltip></el-table-column>
                                <el-table-column prop="autherTag" label="作者" width="150" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="publishCompany" label="出版社" width="180" show-overflow-tooltip> </el-table-column>
                                <el-table-column prop="starLevel" label="评分" width="95"></el-table-column>
                                <el-table-column prop="price" label="售价" width="85"></el-table-column>
                                <el-table-column prop="time" label="出版日期" width="200"></el-table-column>
                                <el-table-column  label="操作" width="130" >
                                <template slot-scope="scope">
                                    <el-button type="warning" icon="el-icon-edit" title="编辑" @click="edit(scope.row)" circle></el-button>
                                    <el-button type="danger" icon="el-icon-delete" title="删除" @click="del(scope.row)" circle></el-button>
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

       <book-info-edit ref="bookInfoEdit" @search="search"></book-info-edit>
       <book-info-add ref="bookInfoAdd" ></book-info-add>
        
    </div>
</template>

<script>
    import Edit from "./bookInfoEdit";
    import Add from "./bookInfoAdd";
    export default {
        components: {
            'book-info-edit': Edit ,
            'book-info-add': Add
        },
        data() {
            return {
                searchForm : {
                },
                bookMetaData:[],
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
                this.$axios.get("http://localhost:8088/bookinfo/bookmeta",{params: {
                'pagesize': this.pagesize,
                'cur_page': this.cur_page,
                'productId': this.searchForm.productCategories,
                'autherName': this.searchForm.autherName,
                'bookName': this.searchForm.bookName,
                'publishCompany': this.searchForm.publishCompany
                }}).then((response) =>{
                //console.log(response.data)
                this.bookMetaData = response.data.bookMeta;
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
            edit(row) {
                this.$refs.bookInfoEdit.open(JSON.stringify(row));
            },
            add: function() {
                this.$refs.bookInfoAdd.open();
            },
            del(row) {
                this.$confirm('确定删除该条数据?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                this.$axios.delete('http://localhost:8088/bookinfo/bookMetaDeleteById',{params: {
                    'id': row.id,
                    'name': row.name,
                    'autherTag': row.autherTag,
                    'publishCompany': row.publishCompany,
                    'starLevel': row.starLevel,
                    'price': row.price,
                    'time': row.time,
                    'brief': row.brief,
                    'applyWorkerId': sessionStorage.getItem("id")
                }})
                   .then((response) => {
                       if (response.data.success == 200) {
                        this.$notify({
                                title: '',
                                message: '删除成功!',
                                type: 'success',
                                duration: 2500
                                });

                       }else {
                        this.$notify.error({
                                title: '',
                                message: '删除失败!',
                                duration: 2500
                                });
                       }
                   })

                    this.search();
                }).catch(() => {
                this.$notify.info({
                    title: '',
                    message: '您已取消删除',
                    duration: 2500
                    });
                })
            },
            search: function() {
                this.$axios.get("http://localhost:8088/bookinfo/bookmeta",{params: {
                'pagesize': this.pagesize,
                'cur_page': this.cur_page
                }}).then((response) => {
                this.bookMetaData = response.data.bookMeta;
                this.totalCount = response.data.total;
                })
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

                this.$axios.get("http://localhost:8088/bookinfo/bookmeta",{params: {
                    'pagesize': this.pagesize,
                    'cur_page': this.cur_page
                    }}).then((response) =>{
                    this.bookMetaData = response.data.bookMeta;
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

