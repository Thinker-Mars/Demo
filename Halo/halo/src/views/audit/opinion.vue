<template>
   <el-dialog title="信息审批" width="70%" :visible.sync="opinionDialogVisible" :before-close="opinionHandleClose">
        <el-row :gutter="20">
            <el-col :span="24" style="min-height:350px;">
                <el-row style="margin-bottom: -20px;">
                    <el-form ref="opinionForm" :rules="rules" :model="opinionForm" label-width="85px">
                        <div class="title_top_all"><strong>&nbsp;&nbsp;&nbsp;业务编号&nbsp;&nbsp;&nbsp;{{this.approveData.workid}} </strong></div>
                        <div class="hr-line-solid-blue" style="width:100%"></div>
                        <el-row>
                            <el-col :span="8">
                                <el-form-item label="审批意见:">
                                    <el-select clearable v-model="approveData.label" prop="label" placeholder="请选择审批意见">
                                        <el-option v-for="item in opinions" :key="item.opinionId" :label="item.label" 
                                        :value="item.opinionId"> </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>

                           <el-col :span="8">
                                <el-form-item label="审批人员:">
                                    <el-select clearable v-model="approveData.workerName" prop="label" placeholder="请选择审批人员">
                                        <el-option v-for="item in workers" :key="item.id" :label="item.workerName"
                                         :value="item.id"> </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>

                            <el-col :span="8">
                                <el-form-item label="申请人">
                                    <el-input v-model.number="approveData.worker" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>
                            
                        </el-row>
                        <div class="title_top_all"><strong>元数据详情</strong></div>
                        <div class="hr-line-solid-blue" style="width:100%"></div>
                        <el-row>
                            <el-col :span="8">
                                <el-form-item label="作者姓名:">
                                    <el-input v-model="approveData.author_tag" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="5">
                                <el-form-item label="售价:">
                                    <el-input v-model.number="approveData.price" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="5">
                                <el-form-item label="评分:">
                                    <el-input v-model.number="approveData.star_level" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                            <el-col :span="9">
                                <el-form-item label="出版社:">
                                    <el-input v-model="approveData.publish_company" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="9">
                                <el-form-item label="出版日期:">
                                    <el-input v-model="approveData.publishtime" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>
                        <el-row>
                            <el-col>
                                <el-form-item label="图书简介:">
                                    <el-input autosize type="textarea" v-model="approveData.brief" :maxlength="1000" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-form>
                </el-row>
            </el-col>

        </el-row>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="audit" round>提交</el-button>
                <el-button type="info" @click="approveClose" round>关闭</el-button>
            </div>
   </el-dialog>
    
</template>
<script>
export default {
    data() {
        return {
            approveData:{},
            opinionForm: {},
            workers:[],
            disabled: true,
            opinions:[{
                label:'同意',
                opinionId: '00'
            },
            {
                label: '不同意',
                opinionId: '01'
            }],
            opinionDialogVisible: false,
            rules: {
                label: [
                    {required: true, message: '请选择审批意见', trigger: 'blur'}
                ]
                
            }


        }
    },
    methods: {
        open: function(data) {
            this.opinionDialogVisible = true;
            this.approveData = JSON.parse(data);
        },
        opinionHandleClose: function() {
            this.approveClose();
        },
        approveClose: function() {
            this.opinionDialogVisible = false;
            this.approveData = {};
        },
        audit: function() {
            this.$axios.post("http://localhost:8088/workflow/auditprocess",{approveDataForm:this.approveData})
            .then((response) => {
                if (response.data.success == 200) {
                        this.$notify({
                                title: '',
                                message: '审批成功!!',
                                type: 'success',
                                duration: 2500
                                });

                       }else {
                        this.$notify.error({
                                title: '',
                                message: '审批失败!',
                                duration: 2500
                                });
                       }
                })
            this.approveClose();
            this.$emit('search');
        }

    },
    created() {
        this.$axios.get("http://localhost:8088/workflow/workerinfo").then((response) => {
            this.workers = response.data;
        }) 

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
