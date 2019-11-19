<template>
    <div>
        <Drawer
            title="我的配置"
            v-model="openFlag"
            width="720"
            :mask-closable="false"
            :styles="styles"
        >
            <Form :model="configForm">
                <Row :gutter="32">
                    <Col span="12">
                        <FormItem label="Name" label-position="top">
                            <Input v-model="configForm.name" placeholder="给服务器起个名字吧" />
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="Ip" label-position="top">
                            <Input v-model="configForm.ip" placeholder="服务器Ip"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row :gutter="32" style="margin-top: 5px">
                    <Col span="12">
                        <FormItem label="Owner" label-position="top">
                            <Select v-model="configForm.owner" placeholder="添加组用户">
                                <Option value="jobs">Steven Paul Jobs</Option>
                                <Option value="ive">Sir Jonathan Paul Ive</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="Port" label-position="top">
                            <Select v-model="configForm.port" placeholder="输入端口">
                                <Option value="private">Private</Option>
                                <Option value="public">Public</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <FormItem label="Protocol" label-position="top">
                            <Select v-model="configForm.protocol" clearable filterable placeholder="选择协议">
                                <Option v-for="item in serverProtocolList" :value="item.configId" :key="item.configId">{{item.configValue}}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="DateTime" label-position="top">
                            <DatePicker v-model="configForm.date" type="daterange" placeholder="please select the date" style="display: block" placement="bottom-end"></DatePicker>
                        </FormItem>
                    </Col>
                </Row>
                <FormItem label="Description" label-position="top">
                    <Input type="textarea" v-model="configForm.desc" :rows="3" placeholder="描述信息" />
                </FormItem>
            </Form>
            <Divider />
            <Table width="687" height="200" :columns="columns1" :data="data1"></Table>
            <div class="demo-drawer-footer">
                <Button style="margin-right: 8px" @click="close()">重置</Button>
                <Button style="margin-right: 8px" @click="close()">取消</Button>
                <Button type="primary" @click="openFlag = false">保存</Button>
            </div>
            <Page :total="100" show-sizer />
        </Drawer>    
    </div>
</template>
<script>
    export default {
        data () {
            return {
                openFlag: false,
                styles: {
                    height: 'calc(100% - 55px)',
                    overflow: 'auto',
                    paddingBottom: '53px',
                    position: 'static'
                },
                serverProtocolList: [],
                configForm: {
                    name: '',
                    url: '',
                    owner: '',
                    type: '',
                    approver: '',
                    date: '',
                    desc: ''
                },
                columns1: [
                    {
                        title: 'Name',
                        key: 'name',
                        width:100,
                        fixed: 'left'
                    },
                    {
                        title: 'Age',
                        width:100,
                        key: 'age'
                    },
                    {
                        title: 'Age',
                        width:100,
                        key: 'age'
                    },
                    {
                        title: 'Age',
                        width:100,
                        key: 'age'
                    },
                    {
                        title: 'Age',
                        width:100,
                        key: 'age'
                    },
                    {
                        title: 'Address',
                        key: 'address',
                        width:100,
                        fixed: 'right'
                    }
                ],
                data1: [
                    {
                        name: 'John Brown',
                        age: 18,
                        address: 'New York No. 1 Lake Park',
                        date: '2016-10-03'
                    },
                    {
                        name: 'Jim Green',
                        age: 24,
                        address: 'London No. 1 Lake Park',
                        date: '2016-10-01'
                    },
                    {
                        name: 'Joe Black',
                        age: 30,
                        address: 'Sydney No. 1 Lake Park',
                        date: '2016-10-02'
                    },
                    {
                        name: 'Jon Snow',
                        age: 26,
                        address: 'Ottawa No. 2 Lake Park',
                        date: '2016-10-04'
                    }
                ]
            }
        },
        methods: {
            /**
             * 打开配置页，然后查询协议
             */
            open() {
                this.openFlag = true;
                //先看缓存里有没有数据
                this.$nextTick(() => {
                    if (this.storage.get('server_protocol')) {
                        this.serverProtocolList = this.storage.get('server_protocol');
                        return;
                    } else {
                        this.searchProtocol();
                    }
                    
                })
            },
            /**
             * 关闭配置页
             */
            close() {
                this.openFlag = false;
            },
            /**
             * 查询协议
             */
            searchProtocol() {
                var protocolData = {
                    typeName: 'server_protocol'
                }
                this.http.get('/config/dict', protocolData)
                .then((res) => {
                    this.serverProtocolList = res;
                    //缓存协议数据
                    this.storage.set('server_protocol', res)
                })
            }
        }
    }
</script>
<style scoped>
    .demo-drawer-footer{
        width: 100%;
        position: absolute;
        bottom: 0;
        left: 0;
        border-top: 1px solid #e8e8e8;
        padding: 10px 16px;
        text-align: right;
        background: #fff;
    }
    /* .myRow{
        position: relative;
        line-height: 20px;
        font-size: 12px;
    } */


</style>