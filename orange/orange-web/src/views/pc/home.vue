<template>
    <div class="layout">
        <Layout>
            <Header>
                <Menu mode="horizontal" theme="dark" active-name="orange" @on-select="m => {headerMenuSelect(m)}">
                    <div class="layout-logo"></div>
                    <div class="layout-nav">
                        
                        <MenuItem name="1">
                            <Icon type="ios-navigate"></Icon>
                            连接
                        </MenuItem>

                        <MenuItem name="2">
                            <Icon type="md-notifications" />
                            <Badge :count=this.msgCount type="success"></Badge>
                            
                        </MenuItem>

                        <MenuItem name="3">
                            <Icon type="ios-analytics"></Icon>
                            占位
                        </MenuItem>

                        <MenuItem name="4"> 
                            <Icon type="md-power" />
                            注销
                        </MenuItem>
                        
                    </div>
                </Menu>
            </Header>
            <Layout>
                <Sider hide-trigger :style="{background: '#fff', height: '83vh', overflow: 'auto'}">
                    <Menu active-name="1-2" theme="light" width="auto" @on-select="m => {siderMenuSelect(m)}">
                        <Submenu name="0">
                            <template slot="title">
                                <Icon type="ios-navigate"></Icon>
                                首页
                            </template>
                            <MenuItem name="1">页面1</MenuItem>
                            <MenuItem name="2">页面2</MenuItem>
                            <MenuItem name="3">页面3</MenuItem>
                        </Submenu>
                        <Submenu name="2">
                            <template slot="title">
                                <Icon type="ios-keypad"></Icon>
                                Item 2
                            </template>
                            <MenuItem name="2-1">Option 1</MenuItem>
                            <MenuItem name="2-2">Option 2</MenuItem>
                        </Submenu>
                        <Submenu name="3">
                            <template slot="title">
                                <Icon type="ios-analytics"></Icon>
                                Item 3
                            </template>
                            <MenuItem name="3-1">Option 1</MenuItem>
                            <MenuItem name="3-2">Option 2</MenuItem>
                        </Submenu>
                    </Menu>
                </Sider>
                <Layout :style="{padding: '0 24px 24px'}">
                    <!-- <Breadcrumb :style="{margin: '24px 0'}">
                        <BreadcrumbItem>Home</BreadcrumbItem>
                        <BreadcrumbItem>Components</BreadcrumbItem>
                        <BreadcrumbItem>Layout</BreadcrumbItem>
                    </Breadcrumb> -->
                    <Content :style="{margin: '80px 0 40px 0'}">
                        <router-view></router-view>
                    </Content>
                </Layout>
                
            </Layout>
            <Footer class="layout-footer-center">2018-2019 &copy; Cone</Footer>
        </Layout>
        
    </div>
</template>


<script>
    export default {
        data() {
            return {
                msgCount: 1
            }

        },
        methods: {
            /**
             * :style="{padding: '24px', minHeight: '280px', background: '#fff'}"
             * 根据选中的标签进行路由
             */
            siderMenuSelect(m) {
                
                if (1 == m) {
                    this.$router.push({path: '/home/news'});
                }
            },
            headerMenuSelect(m) {
                /**
                 * 注销操作
                 */
                if (4 == m) {
                    this.$Modal.confirm({
                        title: '提示',
                        content: '<p>确认退出？</p>',
                        onOk: () => {
                            this.$store.dispatch('clearToken');
                            this.$store.dispatch('changeLoginFlag', false);
                            this.$router.push({path: '/login'});
                        },
                        onCancel: () => {
                            
                        }
                    });

                }
            }
        }
        
    }
</script>

<style scoped>
.layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: auto;
}
.layout-logo{
    width: 100px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
}
.layout-nav{
    width: 420px;
    margin: 0 auto;
    margin-right: 20px;
}
.layout-footer-center{
    text-align: center;
    padding: 20px 50px;
}
.Sider{
 display: block;
 position: absolute;
 left: 0;
 top: 70px;
 bottom: 0;
 overflow-y: scroll;
}
/* .demo-badge{
    width: 42px;
    height: 42px;
    background: #eee;
    border-radius: 6px;
    display: inline-block;
} */
</style>