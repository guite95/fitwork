import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import Swal from 'sweetalert2';
import '@/assets/swal_custom.css'; // 커스텀 CSS 사용

const REST_API_URL = `http://localhost:8080/api-class`;
// const REST_API_URL = `http://192.168.210.83:8080/api-class`;

export const useClassStore = defineStore('class', () => {
    // 상태 선언
    const classList = ref([]);
    const classDetail = ref(null);
    const filteredClasses = ref([]);
    const userRegisteredClasses = ref([]);
    const userMemberedClasses = ref([]);
    const leaderedClasses = ref([]);
    const loading = ref(false);

    // 전체 클래스 목록 가져오기
    const fetchClassList = async () => {
        loading.value = true;
        try {
            const response = await axios.get(`${REST_API_URL}/list/all`, {
                headers: {
                    'Authorization': sessionStorage.getItem('memberToken'),
                }
            });
            classList.value = response.data;
        } catch (error) {
            Swal.fire('에러', '클래스 목록을 불러오는 중 문제가 발생했습니다.', 'error');
        } finally {
            loading.value = false;
        }
    };

    // 위치별 클래스 검색
    const fetchClassesByLocation = async (location) => {
        loading.value = true;
        try {
            const response = await axios.get(`${REST_API_URL}/location/${location}`, {
                headers: {
                    'Authorization': sessionStorage.getItem('memberToken'),
                }
            });
            filteredClasses.value = response.data;
        } catch (error) {
            Swal.fire('에러', '위치별 클래스를 불러오는 중 문제가 발생했습니다.', 'error');
        } finally {
            loading.value = false;
        }
    };

    // 카테고리별 클래스 검색
    const fetchClassesByCategory = async (category) => {
        loading.value = true;
        try {
            const response = await axios.get(`${REST_API_URL}/category/${category}`, {
                headers: {
                    'Authorization': sessionStorage.getItem('memberToken'),
                }
            });
            filteredClasses.value = response.data;
        } catch (error) {
            Swal.fire('에러', '카테고리별 클래스를 불러오는 중 문제가 발생했습니다.', 'error');
        } finally {
            loading.value = false;
        }
    };

    // 성별 필터 클래스 검색
    const fetchClassesByGender = async (gender) => {
        loading.value = true;
        try {
            const response = await axios.get(`${REST_API_URL}/gender/${gender}`, {
                headers: {
                    'Authorization': sessionStorage.getItem('memberToken'),
                }
            });
            filteredClasses.value = response.data;
        } catch (error) {
            Swal.fire('에러', '성별 필터 클래스를 불러오는 중 문제가 발생했습니다.', 'error');
        } finally {
            loading.value = false;
        }
    };

    // 새로운 클래스 등록
    const createClass = async (classData, file) => {
        const formData = new FormData();
        formData.append('classes', JSON.stringify(classData));
        if (file) {
            formData.append('file', file);
        }

        try {
            await axios.post(`${REST_API_URL}/register/class`, formData, {
                headers: {
                    Authorization: sessionStorage.getItem('memberToken'),
                    'Content-Type': 'multipart/form-data'
                },
            });
            Swal.fire('성공', '클래스가 성공적으로 등록되었습니다.', 'success');
        } catch (error) {
            Swal.fire('에러', '클래스를 등록하는 중 문제가 발생했습니다.', 'error');
        }
    };

    // 클래스 수정
    const modifyClass = async (classNo, updatedClass) => {
        try {
            await axios.put(`${REST_API_URL}/modify/${classNo}`, updatedClass, {
                headers: {
                    'Authorization': sessionStorage.getItem('memberToken'),
                    'Content-Type': 'multipart/form-data'
                },
            });
            Swal.fire('성공', '클래스 정보가 수정되었습니다.', 'success');
        } catch (error) {
            Swal.fire('에러', '클래스를 수정하는 중 문제가 발생했습니다.', 'error');
        }
    };

    // 클래스 삭제
    const deleteClass = async (classNo) => {
        try {
            await axios.delete(`${REST_API_URL}/delete/${classNo}`, {
                headers: {
                    'Authorization': sessionStorage.getItem('memberToken'),
                }
            });
            Swal.fire('성공', '클래스가 삭제되었습니다.', 'success');
        } catch (error) {
            Swal.fire('에러', '클래스를 삭제하는 중 문제가 발생했습니다.', 'error');
        }
    };

    // 클래스 등록 거절
    const refuseClassRegistration = async (userId, classNo) => {
        try {
            await axios.delete(`${REST_API_URL}/register/refuse/${userId}/${classNo}`, {
                headers: {
                    'Authorization': sessionStorage.getItem('memberToken'),
                }
            });
            Swal.fire('성공', '클래스 등록이 거절되었습니다.', 'success');
        } catch (error) {
            Swal.fire('에러', '클래스 등록을 거절하는 중 문제가 발생했습니다.', 'error');
        }
    };

    return {
        classList,
        classDetail,
        filteredClasses,
        userRegisteredClasses,
        userMemberedClasses,
        leaderedClasses,
        loading,
        fetchClassList,
        fetchClassesByLocation,
        fetchClassesByCategory,
        fetchClassesByGender,
        createClass,
        modifyClass,
        deleteClass,
        refuseClassRegistration,
    };
});
