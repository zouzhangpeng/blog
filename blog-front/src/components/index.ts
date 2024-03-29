/**
 * 对外暴露全局插件
 */
import type {App} from 'vue'
// 图标
import SvgIcon from './SvgIcon/index.vue'
// 容器
import Container from './Container/index.vue'

const globalComponents = {SvgIcon,Container}
const install = function (app: App) {
    // 注册组件
    Object.entries(globalComponents).forEach(([key, value]) => {
        app.component(key, value)
    })
}
export default install
