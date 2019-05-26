let downloadFile = function($vue,url,fileName,params) {
    $vue.$service.post(url, params || {excelList: [],sign:"mould"} ,{responseType:'blob'}).then((res) => {
        const content = res
        const blob = new Blob([content],{type:"application/octet-stream"})
        if ('download' in document.createElement('a')) { // 非IE下载
          const elink = document.createElement('a');
          elink.download = fileName;
          elink.style.display = 'none';

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

export default  {
    downloadFile
}