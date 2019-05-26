<template>
    <div>
        <el-row>
            <div v-if="showToolbox" class="toolbox">
                <tern-button class="toolbox-btn" v-for="t in toolbox" :icon="t.icon" :type="t.type" :key="t.text"
                             @click="t.click" size="mini">
                    {{t.text}}
                </tern-button>
            </div>
        </el-row>
        <el-row>
            <el-table ref="ternTable"
                      @row-click="rowClick"
                      @select="select"
                      @row-dblclick="rowDblclick"
                      @select-all="selectAll"
                      @current-change="currentChange"
                      @selection-change="selectionChange"
                      @sort-change="sortChange"
                      :cell-style="cellStyle"
                      :empty-text="emptyText"
                      :size="size" :width="width" :height="height"
                      :border="border"
                      :stripe="stripe" :highlight-current-row="highlightCurrentRow"
                      :show-header="showHeader"
                      :fit="fit"
                      v-loading="tableLoading"
                      element-loading-text="数据加载中"
                      :data="record.list">
                <slot></slot>
            </el-table>
        </el-row>
        <el-row style="margin-top: 10px; float: right;" v-if="showPagination">
            <el-pagination
                @current-change="pageCurrentChange"
                @size-change="handleSizeChange"
                :page-sizes="[10, 20, 50, 100, 200, 300, 500]"
                :page-size="record.pageSize"
                layout="total,sizes, prev, pager, next"
                :total="record.totalRow">
            </el-pagination>
        </el-row>
    </div>
</template>

<script>


    import TernButton from "./tern-button.vue";

    export default {
        components: {TernButton},
        name: 'tern-table',
        props: {
            "url": "",
            "data": [Array],
            "handleLoad": false,
            "pageSize": {default: 20, type: Number},
            "pageNumber": {default: 1, type: Number},
            "showToolbox": false,
            "showPagination": {default: true, type: Boolean},
            cellStyle : Function,
            size: String,
            width: [String, Number],
            height: [String, Number],
            maxHeight: [String, Number],
            fit: {
                type: Boolean,
                default: true
            },
            stripe: Boolean,
            border: Boolean,
            emptyText: String,
            highlightCurrentRow: Boolean,
            showHeader:true,
            toolbox: {},
        },
        data() {
            return {
                record: {
                    list: []
                },
                params: {},
                _pageNumber: this.pageNumber,
                _pageSize: this.pageSize,
                tableLoading: false,
            }
        },
        methods: {
            pageCurrentChange(page) {
                this._pageNumber = page;
                this.search({}, (data) => {
                    this.$nextTick(() => {
                        this.$emit("pageCurrentChange", page, data)
                    })
                });

            },
            handleSizeChange(val) {
                this._pageSize = val;
                this.pageCurrentChange(1);
            },
            search(params, callback) {
                this.tableLoading = true;
                if (this.url) {
                    Object.assign(this.params, {
                        pageSize: this._pageSize||20,
                        pageNumber: this._pageNumber||1
                    }, params);

                    this.$service.get(this.url, {
                        params: this.params
                    }).then(res => {
                        this.record = res.data;
                        if (typeof callback == "function") {
                            callback(this.record);
                        }
                        this.$nextTick(()=>{
                            this.tableLoading = false;
                        })
                    }).catch(()=>{
                        //遇到异常则关闭加载
                        this.$nextTick(()=>{
                            this.tableLoading = false;
                        })
                    })
                } else if (this.data) {
                    this.record.list = [];
                    this.$set(this.record,'list',this.data)
                    this.tableLoading = false;
                }
            },
            clear() {
                if (this.record) {
                    this.record = {list: []};
                    this.record.list = [];
                }
            },
            clearSelection(){
                this.$refs.ternTable.clearSelection();
            },
            rowClick(row, event, column) {
                this.$emit("row-click", row, event, column);
            },
            select(selection, row) {
                this.$emit("select", selection, row);
            },
            selectAll(selection) {
                this.$emit("select-all", selection);
            },
            rowDblclick(row, event) {
                this.$emit("row-dblclick", row, event);
            },
            currentChange(currentRow, oldCurrentRow) {
                this.$emit("current-change", currentRow, oldCurrentRow);
            },
            selectionChange(selection) {
                this.$emit("selection-change", selection);
            },
            elTable() {
                return this.$refs.ternTable
            },
            sortChange(column) {
                //列头排序
                let param = {
                    orderBy: ''
                }
                if(column.order == 'ascending'){
                    param.orderBy = column.prop+' asc'
                }else if(column.order == 'descending'){
                    param.orderBy = column.prop+' desc'
                }
                this.search(param)
            }
        },
        created() {
            if (!this.handleLoad) {
                this.search();
            }
        },
        mounted() {

        },
        watch: {
            url: function () {
                this.search();
            },
            pageNumber(newVal) {
                this._pageNumber = newVal;
            },
            pageSize(newVal) {
                this._pageSize = newVal;
            },
            record: {
                handler(newValue) {
                    this.$nextTick(()=>{
                        this.tableLoading = false;
                    })
                },
                deep: true
            }
        }
    }
</script>
<style scope>
    .el-table td, .el-table th {
        padding: 2px 0px 0px 0px;
    }
    .toolbox .toolbox-btn {
        float: right;
        margin-left: 3px;
        margin-bottom: 2px;
    }

    .el-table th {
        font-size: 14px;
        background: #f0f9eb;
    }

</style>
