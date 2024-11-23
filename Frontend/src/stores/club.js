import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import Swal from 'sweetalert2';
import '@/assets/swal_custom.css'; // 커스텀 CSS 사용

// const REST_API_URL = `http://192.168.210.83:8080/api-club`;
const REST_API_URL = `http://lovalhost:8080/api-club`;

// 커스텀 스타일 적용
const customSwal = Swal.mixin({
  customClass: {
    title: 'custom-swal-title',
    text: 'custom-swal-text',
    confirmButton: 'custom-swal-button',
  },
  buttonsStyling: false,
});

export const useClubStore = defineStore('club', () => {
  // 상태 선언
  const clubList = ref([]);
  const clubDetail = ref(null);
  const filteredClubs = ref([]);
  const userRegisteredClubs = ref([]);
  const userMemberedClubs = ref([]);
  const leaderedClubs = ref([]);
  const loading = ref(false);

  // 전체 클럽 목록 가져오기
  const fetchClubList = async () => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/list/all`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      clubList.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '클럽 목록을 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 위치별 클럽 검색
  const fetchClubsByLocation = async (location) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/location/${location}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      filteredClubs.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '위치별 클럽을 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 카테고리별 클럽 검색
  const fetchClubsByCategory = async (category) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/category/${category}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      filteredClubs.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '카테고리별 클럽을 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 성별 필터 클럽 검색
  const fetchClubsByGender = async (gender) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/gender/${gender}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      filteredClubs.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '성별 필터 클럽을 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

// 새로운 클럽 등록
const createClub = async (clubData, file) => {
    const formData = new FormData();
    formData.append('club', new Blob([JSON.stringify(clubData)], { type: 'application/json' }));
    if (file) {
      formData.append('file', file);
    }
  
    try {
      await axios.post(`${REST_API_URL}/register/club`, formData, {
        headers: {
          Authorization: sessionStorage.getItem('memberToken'),
          'Content-Type': 'multipart/form-data',
        },
      });
      customSwal.fire('성공', '클럽이 성공적으로 등록되었습니다.', 'success');
    } catch (error) {
      customSwal.fire('에러', '클럽을 등록하는 중 문제가 발생했습니다.', 'error');
    }
  };
  

  // 클럽 수정
  const modifyClub = async (clubNo, updatedClub) => {
    try {
      await axios.put(`${REST_API_URL}/modify/${clubNo}`, updatedClub, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
          'Content-Type': 'application/json',
        },
      });
      customSwal.fire('성공', '클럽 정보가 수정되었습니다.', 'success');
    } catch (error) {
      customSwal.fire('에러', '클럽을 수정하는 중 문제가 발생했습니다.', 'error');
    }
  };

  // 클럽 삭제
  const deleteClub = async (clubNo) => {
    try {
      await axios.delete(`${REST_API_URL}/delete/${clubNo}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      customSwal.fire('성공', '클럽이 삭제되었습니다.', 'success');
    } catch (error) {
      customSwal.fire('에러', '클럽을 삭제하는 중 문제가 발생했습니다.', 'error');
    }
  };

  // 클럽 등록 거절
  const refuseClubRegistration = async (userId, clubNo) => {
    try {
      await axios.delete(`${REST_API_URL}/register/refuse/${userId}/${clubNo}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      customSwal.fire('성공', '클럽 등록이 거절되었습니다.', 'success');
    } catch (error) {
      customSwal.fire('에러', '클럽 등록을 거절하는 중 문제가 발생했습니다.', 'error');
    }
  };

  return {
    clubList,
    clubDetail,
    filteredClubs,
    userRegisteredClubs,
    userMemberedClubs,
    leaderedClubs,
    loading,
    fetchClubList,
    fetchClubsByLocation,
    fetchClubsByCategory,
    fetchClubsByGender,
    createClub,
    modifyClub,
    deleteClub,
    refuseClubRegistration,
  };
});
