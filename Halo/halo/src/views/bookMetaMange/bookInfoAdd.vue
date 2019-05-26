<template>
   <el-dialog title="图书元数据新增" width="70%" :visible.sync="addDialogVisible" :before-close="addHandleClose" >
      <el-row :gutter="20" v-loading="loading">
            <el-col :span="24" style="min-height:350px;"> 
                    <el-row style="margin-bottom: -20px;">
                        <el-form ref="bookAddForm" :rules="rules" :model="bookAddForm" label-width="85px">
                            <div class="title_top_all"><strong>&nbsp;&nbsp;&nbsp;元数据信息</strong></div>
                            <div class="hr-line-solid-blue" style="width:100%"></div>
                               <el-row>
                                   <el-col :span="8">
                                       <el-form-item label="作者姓名:" prop="autherTag">
                                           <el-input v-model="bookAddForm.autherTag"></el-input>
                                       </el-form-item>
                                   </el-col>

                                   <el-col :span="5">
                                        <el-form-item label="售价:" prop="price">
                                           <el-input v-model.number="bookAddForm.price"></el-input>
                                       </el-form-item>
                                   </el-col>

                                   <el-col :span="7">
                                        <el-form-item label="上架分类:" >
                                            <el-select clearable v-model="bookAddForm.productCategories" placeholder="请选择图书分类">
                                                <el-option v-for="item in productitem" :key="item.productId" :label="item.productCategories" :value="item.productId"> </el-option>
                                            </el-select>
                                       </el-form-item>
                                   </el-col>
                               </el-row>

                               <el-row>
                                     <el-col :span="9">
                                       <el-form-item label="出版社:">
                                           <el-input v-model="bookAddForm.publishCompany"></el-input>
                                       </el-form-item>
                                     </el-col>

                                    <el-col :span="4">
                                       <el-form-item label="评分:">
                                           <el-input v-model="bookAddForm.starLevel"></el-input>                                       
                                       </el-form-item>
                                     </el-col>
                               </el-row>
                               
                               <el-row>
                                     <el-col :span="5">
                                       <el-form-item label="出版日期:" prop="time">
                                             <div class="block">
                                                <el-date-picker
                                                   v-model="bookAddForm.time"
                                                   type="date"
                                                   placeholder="选择日期"
                                                   value-format="yyyy-MM-dd"
                                                   :picker-options="pickerOptions">
                                                </el-date-picker>
                                              </div>
                                       </el-form-item>
                                   </el-col>
                               </el-row>

                               <el-row>
                                    <el-col>
                                       <el-form-item label="图书名称:" prop="name">
                                           <el-input v-model="bookAddForm.name"></el-input>
                                       </el-form-item>
                                   </el-col>
                               </el-row>

                               <el-row>
                                   <el-col>
                                       <el-form-item label="图书简介:">
                                           <el-input autosize type="textarea" v-model="bookAddForm.brief"
                                            :maxlength="1000"></el-input>
                                       </el-form-item>
                                   </el-col>
                               </el-row>
                        </el-form>
                    </el-row>
            </el-col>
      </el-row>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submit" round>提交</el-button>
                <el-button type="info" @click="addClose" round>关 闭</el-button>
            </div>
   </el-dialog>
</template>
<script>
    export default {
        props: {
        },
        data() {
            return {
                bookAddForm: {},
                productitem:[],
                addDialogVisible: false,
                loading:false,
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
                    },
                    rules: {
                        autherTag:[
                            {required: true, message: '请输入作者姓名', trigger: 'blur'}
                        ],
                        price:[
                            { required: true, message: '售价不能为空'},
                            { type: 'number', message: '售价必须为数字'}
                        ],
                        name:[
                            {required: true, message: '请输入图书名称', trigger: 'blur'}
                            
                        ],
                        time:[
                            { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
                        ]
                    }
            };
        },
        computed:{

        },
        methods: {
           addHandleClose: function() {
                this.addClose();
            },
            addClose: function() {//关闭当前dialog
                this.addDialogVisible = false;
                
                
            },
            submit: function() {
                this.bookAddForm.applyWorkerId = sessionStorage.getItem("id");
                this.$axios.post("http://localhost:8088/bookinfo/addbookmeta",{bookAddForm: this.bookAddForm})
                .then((response) => {
                    if (response.data.success == 200) {
                        this.$notify({
                            title: '',
                            message: '成功提交审批!',
                            type: 'success',
                            duration: 2500
                        });
                    }else {
                        this.$notify.error({
                            title: '',
                            message: '新增失败!',
                            duration: 2500
                        })
                    }
                })
                this.addClose();
                this.bookAddForm = {};
            },

            open : function(data){
               this.addDialogVisible = true;
            },

            //清空校验信息
            clear: function() {
                this.$refs['bookAddForm'].resetFields();
            },

        },
        mounted: function () {

        },
        created() {
            this.$axios.get("http://localhost:8088/bookinfo/bookid",{param: {orderBy: "1"}}).then((response) =>{
                this.productitem = response.data;
            });

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
        border-top: 1px solid #20a0ff;
        color: #ffffff;
        background-color: #ffffff;
        height: 1px;
        margin-bottom: 20px;
    }

    .title_top_all {
        height: 22px;
        padding-left: 10px;
        font-size: 16px;
        color: #20a0ff;
        line-height: 22px;
        margin-bottom: 10px;
        /* border-left: 12px solid #20a0ff; */
    }
</style>
