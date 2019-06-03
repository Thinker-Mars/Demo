 <template>
    <div>
       <el-col :span="24">
           <ibox name="ibox">
               <div slot="content" style="width:100%" >
                        <div class="title_top_all"><strong>图书信息Excel导入</strong></div>
                        <div class="hr-line-solid-blue"></div>
                            <el-upload
                                class="upload-demo"
                                drag
                                action="http://localhost:8088/import/Excel"
                                multiple>
                                <i class="el-icon-upload"></i>
                                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                                
                                <div class="el-upload__tip" slot="tip">只能上传Excel文件</div>
                            </el-upload>
                            <el-button type="warning" icon="el-icon-check" title="下载" @click="download"  circle></el-button>
                        
                        
                        
                </div>
           </ibox>
       </el-col>
   </div>
 </template>

 <script>
 export default {
     methods: {
         download: function() {
             var dataList=[];
               this.$axios.post("/apis/import/downloadExcel", {excelList:dataList,sign:"mould"},{responseType:'blob'}).then((res) => {
                      const content = res;
                      const blob = new Blob([content.data],{type:"application/octet-stream"})
                      const fileName = '图书新增信息.xlsx';
                      console.log(content);
                      if ('download' in document.createElement('a')) { // 非IE下载
                      const elink = document.createElement('a');
                      elink.download = fileName;
                      elink.style.display = 'none';

                      //window.open(URL.createObjectURL(blob));
                      var uu = URL.createObjectURL(blob);
                      elink.href = URL.createObjectURL(blob);
                      elink.target = "_blank"
                      document.body.appendChild(elink);
                      elink.click();
                      URL.revokeObjectURL(elink.href); // 释放URL 对象
                      document.body.removeChild(elink);
                    } else { // IE10+下载
                      navigator.msSaveBlob(blob, fileName);
                    }
                });

         }

     },
     created() {
         this.$store.dispatch('setNewName',sessionStorage.getItem("name"));
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

 
 
