<template>
   <el-dialog title="审批详情内容" width="70%" :visible.sync="auditDetailDialogVisible" :before-close="auditDetailHandleClose">
        <el-row :gutter="20">
            <el-col :span="24" style="min-height:350px;">
                <el-row style="margin-bottom: -20px;">
                    <el-form ref="auditForm"  :model="auditData" label-width="85px">
                        <div class="title_top_all"><strong>&nbsp;&nbsp;&nbsp;业务编号&nbsp;&nbsp;&nbsp;{{this.workid}} </strong></div>
                        <div class="hr-line-solid-blue" style="width:100%"></div>
                        <el-row>
                            <el-col :span="8">
                                <el-form-item label="审批意见:">
                                    <el-input v-model.number="auditData.result" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>

                           <el-col :span="8">
                                <el-form-item label="审批人员:">
                                    <el-input v-model.number="auditData.name" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="8">
                                <el-form-item label="审批时间:">
                                    <el-input v-model.number="auditData.audit_time" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>
                            
                        </el-row>
                        <div class="title_top_all"><strong>申请信息</strong></div>
                        <div class="hr-line-solid-blue" style="width:100%"></div>
                        <el-row>
                            <el-col :span="8">
                                <el-form-item label="作者姓名:">
                                    <el-input v-model="auditData.author_tag" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="5">
                                <el-form-item label="售价:">
                                    <el-input v-model.number="auditData.price" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="5">
                                <el-form-item label="评分:">
                                    <el-input v-model.number="auditData.star_level" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                            <el-col :span="9">
                                <el-form-item label="出版社:">
                                    <el-input v-model="auditData.publish_company" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="9">
                                <el-form-item label="出版日期:">
                                    <el-input v-model="auditData.publish_time" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>
                        <el-row>
                            <el-col>
                                <el-form-item label="图书简介:">
                                    <el-input autosize type="textarea" v-model="auditData.brief" :maxlength="1000" disabled="disabled"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-form>
                </el-row>
            </el-col>

        </el-row>
            <div slot="footer" class="dialog-footer">
                <el-button type="info" @click="auditClose" round>关闭</el-button>
            </div>
   </el-dialog>
    
</template>
<script>
export default {
    data() {
        return {
            auditDetailDialogVisible: false,
            auditData: {},
            workid: ''

        }
    },
    methods: {
        auditDetailHandleClose: function() {
            this.auditClose();
        },
        auditClose: function() {
            this.auditDetailDialogVisible = false;
        },
        open: function(data) {
            this.auditDetailDialogVisible = true;
            this.workid = JSON.parse(data).workid;
            this.auditResult = JSON.parse(data).auditResult;
            this.searchApplyInfo();
        },
        searchApplyInfo: function() {
            this.$axios.get("http://localhost:8088/workflow/myapplyInfo",{params: {
                    'workid': this.workid,
                    'auditResult': this.auditResult
                }})
                .then((response) => {
                    this.auditData = response.data;
                })
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
