<template>
    <div>
        <div>
            <label for="period" class="block text-darkBlue font-title mb-2">기간 선택</label>
            <select id="period" v-model="selectedPeriod" @change="fetchStats"
                class="w-full px-4 py-3 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-lightBlue bg-white text-gray-500 font-title"
                required>
                <option value="daily">일별 (최근 7일)</option>
                <option value="weekly">주별 (최근 4주)</option>
                <option value="monthly">월별 (최근 3개월)</option>
            </select>
        </div>
        <div ref="chart" style="width: 700px; height: 750px;" class="mt-6 mx-auto flex items-center justify-center">
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import * as echarts from 'echarts'; // 전체 ECharts 라이브러리를 가져오기
import dayjs from 'dayjs'; // 날짜 형식 변환을 위한 dayjs 라이브러리 (추가 설치 필요)

// 상태 정의
const selectedPeriod = ref('daily');  // 선택된 기간 ('daily', 'weekly', 'monthly')
const chart = ref(null);  // 차트를 그릴 div 요소 참조
let chartInstance = null;  // ECharts 인스턴스

// 기간에 따른 차트 제목 설정
const getChartTitle = (period) => {
    switch (period) {
        case 'daily':
            return '일별 방문자 통계';
        case 'weekly':
            return '주별 방문자 통계';
        case 'monthly':
            return '월별 방문자 통계';
        default:
            return '방문자 통계';
    }
};

// 방문자 통계 데이터를 서버로부터 가져오는 함수
const fetchStats = async () => {
    try {
        const period = selectedPeriod.value;

        // API 호출 시 쿼리 스트링 없이 단순히 선택한 기간에 맞는 URL 사용
        const response = await fetch(`/api-admin/${period}`);

        // 응답 상태 코드가 200인지 확인
        if (!response.ok) {
            throw new Error(`Failed to fetch stats: ${response.status} - ${response.statusText}`);
        }

        // 응답의 content-type이 JSON인지 확인
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            const data = await response.json();
            if (data.length === 0) {
                updateChart([], [], '데이터가 없습니다.');
            } else {
                let labels;
                if (period === 'daily') {
                    // 일별 통계의 경우 날짜를 그대로 사용
                    labels = data.map((item) => item.visitDate || '날짜 없음');
                } else if (period === 'weekly') {
                    // 주별 통계의 경우 "X주 전" 형식으로 레이블을 설정
                    labels = data.map((item, index) => {
                        if (index === 0) return '이번 주';
                        return `${index}주 전`;
                    });
                } else if (period === 'monthly') {
                    // 월별 통계의 경우 "X개월 전" 형식으로 레이블을 설정
                    labels = data.map((item, index) => {
                        if (index === 0) return '이번 달';
                        return `${index}개월 전`;
                    });
                }

                const visitorCounts = data.map((item) => item.visitorCount || 0);
                updateChart(labels.reverse(), visitorCounts.reverse()); // 최근 데이터를 오른쪽으로 배치
            }
        } else {
            // 비 JSON 응답을 확인하기 위해 추가적인 로그 출력
            const textResponse = await response.text();  // JSON이 아닐 경우 텍스트로 읽기
            console.error('Received non-JSON response:', textResponse);
            throw new Error("Received non-JSON response");
        }
    } catch (error) {
        console.error('Failed to fetch stats:', error);
        updateChart([], [], '데이터를 가져오는 데 실패했습니다.');
    }
};

// 차트 업데이트 함수 (가져온 데이터를 이용해 차트를 업데이트)
const updateChart = (labels, visitorCounts, errorMessage = null) => {
    const option = {
        title: {
            text: errorMessage || getChartTitle(selectedPeriod.value),
            left: 'center',
            top: 'top',
        },
        tooltip: {
            trigger: 'axis',
        },
        xAxis: {
            type: 'category',
            data: labels,
        },
        yAxis: {
            type: 'value',
            // axisLine: {
            //     show: false  // 세로축 라인 제거
            // },
            splitLine: {
                show: false  // 가로선을 제거
            }
        },
        series: [
            {
                data: visitorCounts,
                type: 'bar',  // 막대그래프 타입으로 변경
                name: '방문자 수',
                barWidth: '30%',  // 막대 너비를 가늘게 설정
                animationEasing: 'bounceOut', // 애니메이션 추가
                animationDuration: 1000, // 애니메이션 지속 시간
                itemStyle: {
                    color: '#93AAFD',
                },
            },
        ],
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true,
        },
    };

    // 차트에 옵션 적용
    chartInstance.setOption(option);
};

// 컴포넌트가 마운트된 후 차트 초기화 및 데이터 로드
onMounted(() => {
    if (!chart.value) {
        console.error('Chart DOM element not found.');
        return;
    }
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
