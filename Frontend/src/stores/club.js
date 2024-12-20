import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import Swal from 'sweetalert2';
import '@/assets/swal_custom.css'; // 커스텀 CSS 사용

const REST_API_URL = `http://192.168.210.83:8080/api-club`;
// const REST_API_URL = `http://localhost:8080/api-club`;

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
  // 상황 선정
  const clubList = ref([]);
  const clubDetail = ref(null);
  const filteredClubs = ref([]);
  const userRegisteredClubs = ref([]);
  const userMemberedClubs = ref([]);
  const leaderedClubs = ref([]);
  const loading = ref(false);
  const isRegisted = ref(false);

  // 전체 클럽 목록 검색
  const getClubList = async () => {
    loading.value = true;
    try {
      await axios.get(`${REST_API_URL}/list/all`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      }).then((response) => {
        clubList.value = response.data;
      })
    } catch (error) {
      customSwal.fire('에러', '클럽 목록을 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 위치별 클럽 검색
  const getClubsByLocation = async (location) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/list/location/${location}`);
      filteredClubs.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '위치별 클럽를 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 카테고리별 클럽 검색
  const getClubsByCategory = async (category) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/list/category/${category}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      filteredClubs.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '카테고리별 클럽를 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 성별 필터 클럽 검색
  const getClubsByGender = async (gender) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/list/gender/${gender}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      filteredClubs.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '성별 필터 클럽를 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 회원이 신청한 클럽 조회
  const getClubsById = async (id) => {
    try {
      const response = await axios.get(`${REST_API_URL}/list/registed/${id}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        }
      });
      userRegisteredClubs.value = response.data;
    } catch {
      customSwal.fire('에러', '신청한 클럽을 조회하는 중 문제가 발생했습니다', 'error')
    }
  };

  // 회원이 개설한 클럽 조회
  const getClubsByLeader = async (leader) => {
    try {
      const response = await axios.get(`${REST_API_URL}/list/leader/${leader}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        }
      });
      leaderedClubs.value = response.data;
    } catch {
      customSwal.fire('에러', '개설한 클럽을 조회하는 중 문제가 발생했습니다', 'error')
    }
  }

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
const modifyClub = async (clubNo, updatedClub, file) => {
  try {
    const formData = new FormData();
    formData.append('club', new Blob([JSON.stringify(updatedClub)], { type: 'application/json' }));
    if (file) {
      formData.append('file', file);
    }

    await axios.put(`${REST_API_URL}/modify/${clubNo}`, formData, {
      headers: {
        'Authorization': sessionStorage.getItem('memberToken'),
        'Content-Type': 'multipart/form-data',
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

  // 클럽 세부 정보 검색
  const getClubDetail = async (clubNo) => {
    loading.value = true;
    try {
      const response = await axios.get(`${REST_API_URL}/detail/${clubNo}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      });
      clubDetail.value = response.data;
    } catch (error) {
      customSwal.fire('에러', '클럽 세부 정보를 불러오는 중 문제가 발생했습니다.', 'error');
    } finally {
      loading.value = false;
    }
  };

  // 클럽 가입 신청
  const registerClub = async (id, clubNo) => {
    try {
      await axios.post(`${REST_API_URL}/register/${id}/${clubNo}`, null, {
        headers: {
          Authorization: sessionStorage.getItem('memberToken'),
        },
      });
      registStatus(id, clubNo);
      customSwal.fire('성공', '클럽 가입 신청이 완료되었습니다.', 'success');
    } catch (error) {
      customSwal.fire('에러', '클럽 가입 신청 중 문제가 발생했습니다.', 'error');
      throw error; // 에러가 필요한 경우 상위 컴포넌트로 전달
    }
  };
  
  // 클럽 신청 취소
  const cancelRegistClub = async (id, clubNo) => {
    try {
      await axios.delete(`${REST_API_URL}/register/cancel/${id}/${clubNo}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        }
      });
      registStatus(id, clubNo);
      customSwal.fire('성공', '클럽 신청이 취소되었습니다', 'success');
    } catch (error) {
      customSwal.fire('에러', '클럽 신청 취소 중 문제가 발생했습니다', 'error')
      throw error;
    }
  };

  const registStatus = async (id, clubNo) => {
    try {
      const response = await axios.get(`${REST_API_URL}/register/status/${id}/${clubNo}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
        },
      })
      isRegisted.value = response.data;
    } catch (error) {
      console.error(error)
    }
  }

  return {
    clubList,
    clubDetail,
    filteredClubs,
    userRegisteredClubs,
    userMemberedClubs,
    leaderedClubs,
    loading,
    isRegisted,
    getClubList,
    getClubsByLocation,
    getClubsByCategory,
    getClubsByGender,
    createClub,
    modifyClub,
    deleteClub,
    refuseClubRegistration,
    getClubDetail,
    getClubsById,
    getClubsByLeader,
    registerClub,
    cancelRegistClub,
    registStatus,
  };
});
