<template>
    <div>
        <select v-model="selectedPeriod" @change="fetchStats">
            <option value="daily">일별</option>
            <option value="weekly">주별</option>
            <option value="monthly">월별</option>
        </select>
        <div ref="chart" style="width: 600px; height: 400px;"></div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import * as echarts from 'echarts/core';

// 필요한 차트 타입과 구성 요소만 가져오기
import { LineChart } from 'echarts/charts';
import {
    TitleComponent,
    TooltipComponent,
    GridComponent,
    LegendComponent,
} from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

// 등록
echarts.use([
    TitleComponent,
    TooltipComponent,
    GridComponent,
    LineChart,
    CanvasRenderer,
]);

const selectedPeriod = ref('daily');  // 선택된 기간 ('daily', 'weekly', 'monthly')
const chart = ref(null);  // 차트를 그릴 div 요소 참조
let chartInstance = null;  // ECharts 인스턴스

// 방문자 통계 데이터를 서버로부터 가져오는 함수
const fetchStats = async () => {
    try {
        const response = await fetch(`/api/visitor-stats/${selectedPeriod.value}`);
        const data = await response.json();
        updateChart(data);
    } catch (error) {
        console.error('Failed to fetch stats:', error);
    }
};

// 차트 업데이트 함수 (가져온 데이터를 이용해 차트를 업데이트)
const updateChart = (data) => {
    // 데이터에서 라벨과 방문자 수를 추출
    const labels = data.map(item => item.date || item.week || item.month);
    const visitorCounts = data.map(item => item.visitor_count);

    // 차트 옵션 설정
    const option = {
        xAxis: {
            type: 'category',
            data: labels,
        },
        yAxis: {
            type: 'value',
        },
        series: [
            {
                data: visitorCounts,
                type: 'line',
                smooth: true,
            },
        ],
    };

    // 차트에 옵션 적용
    chartInstance.setOption(option);
};

// 컴포넌트가 마운트된 후 차트 초기화 및 데이터 로드
onMounted(() => {
    chartInstance = echarts.init(chart.value);  // 차트 초기화
    fetchStats();  // 초기 데이터 로드
});

// 선택된 기간이 변경될 때 데이터를 다시 로드
watch(selectedPeriod, fetchStats);
</script>

<style scoped>
select {
    margin-bottom: 20px;
}
</style>