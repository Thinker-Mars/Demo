<template>
    <div class="tern-form" onkeydown="if(event.keyCode==13){return false;}">
        <slot></slot>
    </div>
</template>

<script>
    export default {
        props: {
            url: {
                default: "",
                type: String,
            },
            submitFlag: {
                default: null,
                type: String,
            }


        },
        name: 'tern-form',
        data() {
            return {}
        },
        methods: {
            update(form) {
                return this.$service.put(this.url, form)
            },
            insert(form) {
                return this.$service.post(this.url, form)
            },
        },
        created() {
        },
    }
</script>

<style scoped="">
    .tern-form .el-form-item {
        margin-bottom: 6px;
    }

    .el-input__inner {
        height: 32px;
    }

</style>
