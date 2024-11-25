import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import Swal from 'sweetalert2';
import '@/assets/swal_custom.css'; // 커스텀 CSS 사용

// const REST_API_URL = `http://192.168.210.83:8080/api-class`;
const REST_API_URL = `http://localhost:8080/api-class`;

// 커스텀 스타일 적용
const customSwal = Swal.mixin({
  customClass: {
    title: 'custom-swal-title',
    text: 'custom-swal-text',
    confirmButton: 'custom-swal-button',
  },
  buttonsStyling: false,
});

export const useClassStore = defineStore('class', () => {
  // 상태 정의
  const classList = ref([]);
  const classDetail = ref(null);
  const filteredClasses = ref([]);
  const userRegisteredClasses = ref([]);
  const userMemberedClasses = ref([]);
  const leaderedClasses = ref([]);
  const loading = ref(false);

  // 전체 클래스 목록 가져오기
  const getClassList = async () => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/list/all`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      classList.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '클래스 목록을 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 클래스 세부 정보 가져오기
  const getClassDetail = async (classNo) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/detail/${classNo}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      classDetail.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '클래스 세부 정보를 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 위치별 클래스 검색
  const getClassesByLocation = async (location) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/list/location/${location}`);
      filteredClasses.value = response.data;
      console.log(filteredClasses.value)
    } catch (error) {
      customSwal.fire('에러', '위치별 클래스를 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 카테고리별 클래스 검색
  const getClassesByCategory = async (category) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/list/category/${category}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        }
      });
      filteredClasses.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '카테고리별 클래스를 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 성별 필터 클래스 검색
  const getClassesByGender = async (gender) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/list/gender/${gender}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        }
      });
      filteredClasses.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '성별 필터 클래스를 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 회원이 신청한 클래스 조회
  const getClassesById = async (id) => {
    try {
      const response = await axios.get(`${REST_API_URL}/list/registed/${id}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        }
      });
      userRegisteredClasses.value = response.data;
    } catch {
      customSwal.fire('에러', '신청한 클래스를 조회하는 중 문제가 발생했습니다', 'error')
    }
  };

  // 강사가 개설한 클래스 조회
  const getClassesByLeader = async (leader) => {
    try {
      const response = await axios.get(`${REST_API_URL}/list/leader/${leader}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        }
      });
      leaderedClasses.value = response.data;
    } catch {
      // customSwal.fire('에러', '개설한 클래스를 조회하는 중 문제가 발생했습니다', 'error')
    }
  }

  // 새로운 클래스 등록
  const createClass = async (classData, file) => {
    const formData = new FormData();
    formData.append('classes', new Blob([JSON.stringify(classData)], { type: 'application/json' }));
    if (file) {
      formData.append('file', file);
    }
    console.log(classData)

    try {
      await axios.post(`${REST_API_URL}/register/class`, formData, {
        headers: {
          Authorization: sessionStorage.getItem('memberToken'),
          'Content-Type': 'multipart/form-data',
        },
      });
      customSwal.fire('성공', '클래스가 성공적으로 등록되었습니다.', 'success');
    } catch (error) {
      console.log(error)
      customSwal.fire('에러', '클래스를 등록하는 중 문제가 발생했습니다.', 'error');
    }
  };

  // 클래스 수정
  const modifyClass = async (classNo, updatedClass, file) => {
    try {
      const formData = new FormData();
      formData.append('classes', new Blob([JSON.stringify(updatedClass)], { type: 'application/json' }));
      if (file) {
        formData.append('file', file);
      }

      await axios.put(`${REST_API_URL}/modify/${classNo}`, formData, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
          'Content-Type': 'multipart/form-data',
        },
      });
      customSwal.fire('성공', '클래스 정보가 수정되었습니다.', 'success');
    } catch (error) {
      customSwal.fire('에러', '클래스를 수정하는 중 문제가 발생했습니다.', 'error');
    }
  };

  // 클래스 삭제
  const deleteClass = async (classNo) => {
    try {
      await axios.delete(`${REST_API_URL}/delete/${classNo}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      customSwal.fire('성공', '클래스가 삭제되었습니다.', 'success');
    } catch (error) {
      customSwal.fire('에러', '클래스를 삭제하는 중 문제가 발생했습니다.', 'error');
    }
  };

  // 클래스 등록 거절
  const refuseClassRegistration = async (userId, classNo) => {
    try {
      await axios.delete(`${REST_API_URL}/register/refuse/${userId}/${classNo}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      customSwal.fire('성공', '클래스 등록이 거절되었습니다.', 'success');
    } catch (error) {
      customSwal.fire('에러', '클래스 등록을 거절하는 중 문제가 발생했습니다.', 'error');
    }
  };

  // 클래스 가입 신청
  const registerClass = async (userId, classNo) => {
    try {
      await axios.post(`${REST_API_URL}/register/${userId}/${classNo}`, null, {
        headers: {
          Authorization: sessionStorage.getItem('memberToken'),
        },
      });
      customSwal.fire('성공', '클래스 가입 신청이 완료되었습니다.', 'success');
    } catch (error) {
      customSwal.fire('에러', '클래스 가입 신청 중 문제가 발생했습니다.', 'error');
      throw error; // 에러가 필요한 경우 상위 컴포넌트로 전달
    }
  };

  // 클래스 신청 취소
  const cancelRegistClass = async (id, classNo) => {
    try {
      await axios.delete(`${REST_API_URL}/register/cancel/${id}/${classNo}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        }
      });
      customSwal.fire('성공', '클래스 신청이 취소되었습니다', 'success');
    } catch (error) {
      customSwal.fire('에러', '클래스 신청 취소 중 문제가 발생했습니다', 'error')
      throw error;
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
    getClassList,
    getClassesByLocation,
    getClassesByCategory,
    getClassesByGender,
    createClass,
    modifyClass,
    deleteClass,
    refuseClassRegistration,
    getClassDetail,
    getClassesById,
    getClassesByLeader,
    registerClass,
    cancelRegistClass,
  };
});
