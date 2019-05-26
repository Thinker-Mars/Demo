<template>
    <el-button v-show="btnAuthor == true"
        @click="handleClick"
        :size="size"
        :type="type"
        :plain="true"
        :round="false"
        :loading="loading"
        :disabled="disabled"
        :icon="icon"
        :autofocus="autofocus"
        :native-type="nativeType">
        <slot></slot>
    </el-button>
</template>

<script>
    export default {
        props: {
            type: {
                type: String,
                default: 'default'
            },
            size: String,
            icon: {
                type: String,
                default: ''
            },
            nativeType: {
                type: String,
                default: 'button'
            },
            loading: Boolean,
            disabled: Boolean,
            plain: Boolean,
            autofocus: Boolean,
            round: Boolean,
            buttonCode: String
        },
        name: 'tern-button',
        computed: {
            btnAuthor() {
                var authorBtn = this.$store.getters.user.authorButtonList;
                // return true;
                if(this.buttonCode=="normal")return true;
                if(!authorBtn)return false;
                return authorBtn.some((item)=>{
                    return item.BUTTON_CODE == this.buttonCode;
                })
            }
        },
        data() {
            return {}
        },
        methods: {
            handleClick(evt) {
                this.$emit('click', evt);
            },
            handleInnerClick(evt) {
                if (this.disabled) {
                    evt.stopPropagation();
                }
            }
        },
        created() {
        },
    }
</script>

<style>
</style>
