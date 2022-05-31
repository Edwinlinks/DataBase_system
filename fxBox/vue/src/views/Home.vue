<template>
  <div style="padding: 10px">
    <el-card>
      <div id="myChart" :style="{width: '800px', height: '600px'}"></div>
    </el-card>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Home",
  data() {
    return {}
  },
  mounted() {
    this.drawLine();
  },
  methods: {
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$root.echarts.init(document.getElementById('myChart'))
      let option = {
        title: {
          text: '物品数量比例统计图',
          left: 'left'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          trigger: 'item'
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        series: [
          {
            name: '物品比例',
            type: 'pie',
            radius: [50, 250],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: []
          }
        ]
      }
      request.get("/admin/count").then(res => {
        if (res.code === '0') {
          res.data.forEach(item => {
            option.series[0].data.push({name: item.address, value: item.count})
          })
          // 绘制图表
          myChart.setOption(option);
        }
      })

    }
  }
}
</script>

<style scoped>

</style>
