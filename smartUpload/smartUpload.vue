<template>
    <div class="smartUpload">
        <input type="file" id="smartUpload" @change.prevent.stop="e_upload" accept=".txt,.csv" class="invisible"/>
        <div class="actual" id="actual-upload">
            <span>点击上传</span>
        </div>

    </div>
</template>

<script>
import SmartReader from "./lib/index";
export default {
    /**
     * 需为dom设置监听，所以在此阶段进行
     */
    mounted() {
        document.getElementById("actual-upload").addEventListener("click", function() {
            document.getElementById("smartUpload").click();
        }, true);
    },
    methods: {
        e_upload() {
            /** 通知父组件或调用方，已有文件上传，自行进行相应处理 */
            this.$emit("onLoadStart");
            var input = document.getElementById("smartUpload");
            var reader = new SmartReader(input);
            reader.start().then(res => {
                this.$emit("onLoadEnd", res);
                /** 避免第二次不触发事件 */
                input.value = "";
            });
        }
    }
}
</script>

<style scoped rel="stylesheet/scss" lang="scss">
    .smartUpload {
        display: inline-block;
        position: relative;
        height: 28px;
        .invisible {
            display: none
        }
        .actual {
            box-sizing: border-box;
            width: 70px;
            height: 28px;
            border: 1px solid #1d5af2;
            border-radius: 4px;
            text-align: center;
            line-height: 28px;
            color: #1d5af2;
            &:hover {
                cursor: pointer;
                background-color: #1d5af2;
                color: #ffffff;
            }
        }
    }
</style>