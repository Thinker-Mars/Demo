<template>
   <el-dialog title="图书元数据管理" width="70%" :visible.sync="editDialogVisible" :before-close="editHandleClose" >
      <el-row :gutter="20" v-loading="loading">
            <el-col :span="24" style="min-height:350px;"> 
                    <el-row style="margin-bottom: -20px;">
                        <el-form ref="bookMetaForm" :rules="rules" :model="bookMetaForm" label-width="85px">
                            <div class="title_top_all"><strong>&nbsp;&nbsp;&nbsp;编号{{this.bookMetaForm.id}} </strong></div>
                            <div class="hr-line-solid-blue" style="width:100%"></div>
                               <el-row>
                                   <el-col :span="8">
                                       <el-form-item label="作者姓名:" prop="autherTag">
                                           <el-input v-model="bookMetaForm.autherTag"></el-input>
                                       </el-form-item>
                                   </el-col>

                                   <el-col :span="5">
                                        <el-form-item label="售价:" prop="price">
                                           <el-input v-model.number="bookMetaForm.price"></el-input>
                                       </el-form-item>
                                   </el-col>
                               </el-row>

                               <el-row>
                                     <el-col :span="9">
                                       <el-form-item label="出版社:">
                                           <el-input v-model="bookMetaForm.publishCompany"></el-input>
                                       </el-form-item>
                                   </el-col>
                               </el-row>
                               
                               <el-row>
                                     <el-col :span="5">
                                       <el-form-item label="出版日期:" prop="time">
                                             <div class="block">
                                                <el-date-picker
                                                   v-model="bookMetaForm.time"
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
                                           <el-input v-model="bookMetaForm.name"></el-input>
                                       </el-form-item>
                                   </el-col>
                               </el-row>

                               <el-row>
                                   <el-col>
                                       <el-form-item label="图书简介:">
                                           <el-input autosize type="textarea" v-model="bookMetaForm.brief" :maxlength="1000"></el-input>
                                       </el-form-item>
                                   </el-col>
                               </el-row>
                        </el-form>
                    </el-row>
            </el-col>
      </el-row>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary"@click="onSubmit" round>保存</el-button>
                <el-button type="info" @click="editClose" round>关 闭</el-button>
            </div>
   </el-dialog>
</template>
<script>
    export default {
        props: {
        },
        data() {
            return {
                bookMetaForm: {},
                editDialogVisible: false,
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
           editHandleClose: function() {
                this.editClose();
            },
            editClose: function() {//关闭当前dialog
                this.editDialogVisible = false;
            },

            open : function(data){
               this.editDialogVisible = true;
               this.bookMetaForm = JSON.parse(data);
            },

            //清空校验信息
            clear: function() {
                this.$refs['bookMetaForm'].resetFields();
            },
            onSubmit: function() {
                this.editDialogVisible = false;
                this.bookMetaForm.applyWorkerId = sessionStorage.getItem("id");
                this.$axios.post("http://localhost:8088/bookinfo/bookmetauppdate",{bookMetaForm: this.bookMetaForm})
                .then((response) =>{
                    if (response.data.success == 200) {
                        this.$notify({
                            title: '',
                            message: '修改成功!',
                            type: 'success',
                            duration: 2500
                        });
                    }else {
                        this.$notify.error({
                            title: '',
                            message: '修改失败!',
                            duration: 2500
                        })

                    }
                    this.$emit('search');
                })
            }

        },
        mounted: function () {

        },
        created() {

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
