import { computed, ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';
import Swal from 'sweetalert2';
import '@/assets/swal_custom.css';

// const REST_API_URL = `http://localhost:8080/api-member`;
const REST_API_URL = `http://192.168.210.83:8080/api-member`;

export const useMemberStore = defineStore('member', () => {
    const memberToken = ref(null);
    const memberId = ref(null);
    const memberName = ref(null);
    const memberNickname = ref(null);
    const memberGender = ref(null);
    const memberBirthDate = ref(null);
    const memberAge = ref(null);
    const memberEmail = ref(null);
    const memberPhoneNumber = ref(null);
    const memberAddress = ref(null);
    const memberRole = ref(null);

    const setMemberToken = (token) => {
        memberToken.value = token;
        sessionStorage.setItem('memberToken', token);
    };

    const clearMemberToken = () => {
        memberToken.value = null;
        memberId.value = null;
        memberName.value = null;
        memberNickname.value = null;
        memberGender.value = null;
        memberBirthDate.value = null;
        memberAge.value = null;
        memberEmail.value = null;
        memberPhoneNumber.value = null;
        memberAddress.value = null;
        memberRole.value = null;

        sessionStorage.removeItem('memberToken');
        sessionStorage.removeItem('memberId');
        sessionStorage.removeItem('memberName');
        sessionStorage.removeItem('memberNickname');
        sessionStorage.removeItem('memberGender');
        sessionStorage.removeItem('memberBirthDate');
        sessionStorage.removeItem('memberAge');
        sessionStorage.removeItem('memberEmail');
        sessionStorage.removeItem('memberPhoneNumber');
        sessionStorage.removeItem('memberAddress');
        sessionStorage.removeItem('memberRole');
    };

    const loadMemberToken = () => {
        const token = sessionStorage.getItem('memberToken');
        if (token) {
            memberToken.value = token;
            loadMemberFromSession();
        }
    };

    const loadMemberInfo = (id) => {
        if (memberToken.value) {
            axios.get(`${REST_API_URL}/${id}`, {
                headers: {
                    "Authorization": sessionStorage.getItem('memberToken')
                },
            })
                .then((response) => {
                    const data = response.data;

                    // Pinia 상태 업데이트
                    memberId.value = data.id;
                    memberName.value = data.name;
                    memberNickname.value = data.nickname;
                    memberGender.value = data.gender;
                    memberBirthDate.value = data.birthDate;
                    memberAge.value = data.age;
                    memberEmail.value = data.email;
                    memberPhoneNumber.value = data.phoneNumber;
                    memberAddress.value = data.address;
                    memberRole.value = data.memberRole;

                    // 세션에 사용자 정보 저장
                    sessionStorage.setItem('memberId', data.id);
                    sessionStorage.setItem('memberName', data.name);
                    sessionStorage.setItem('memberNickname', data.nickname);
                    sessionStorage.setItem('memberGender', data.gender);
                    sessionStorage.setItem('memberBirthDate', data.birthDate);
                    sessionStorage.setItem('memberAge', data.age);
                    sessionStorage.setItem('memberEmail', data.email);
                    sessionStorage.setItem('memberPhoneNumber', data.phoneNumber);
                    sessionStorage.setItem('memberAddress', data.address);
                    sessionStorage.setItem('memberRole', data.memberRole);

                })
                .catch((error) => {
                    console.error('사용자 정보를 불러오는 중 오류가 발생했습니다:', error);
                });
        }
    };

    const loadMemberFromSession = () => {
        memberId.value = sessionStorage.getItem('memberId');
        memberName.value = sessionStorage.getItem('memberName');
        memberNickname.value = sessionStorage.getItem('memberNickname');
        memberGender.value = sessionStorage.getItem('memberGender');
        memberBirthDate.value = sessionStorage.getItem('memberBirthDate');
        memberAge.value = sessionStorage.getItem('memberAge');
        memberEmail.value = sessionStorage.getItem('memberEmail');
        memberPhoneNumber.value = sessionStorage.getItem('memberPhoneNumber');
        memberAddress.value = sessionStorage.getItem('memberAddress');
        memberRole.value = sessionStorage.getItem('memberRole');
    };

    const login = async (id, password) => {
        try {
            const response = await axios.post(`${REST_API_URL}/login`, { id, password });
            const token = response.headers['authorization'];

            if (token) {
                setMemberToken(token);
                await loadMemberInfo(id); // 사용자 정보 로드 후 리다이렉트
                router.push({ name: 'home' }); // 홈으로 이동
            } else {
                console.error('로그인에 실패했습니다. 토큰이 없습니다.');
                throw new Error('로그인에 실패했습니다. 토큰이 없습니다.');
            }
        } catch (error) {
            console.error('로그인 요청 중 오류가 발생했습니다:', error);
            throw error;
        }
    };

    const modifyMember = async (updatedInfo) => {
        if (memberToken.value && memberId.value) {
            try {
                await axios.put(`${REST_API_URL}/${memberId.value}`, updatedInfo, {
                    headers: {
                        "Authorization": sessionStorage.getItem('memberToken'),
                        "Content-Type": "application/json"
                    }
                });

                // Pinia 상태 및 세션에 업데이트된 사용자 정보 반영
                memberNickname.value = updatedInfo.nickname;
                memberPhoneNumber.value = updatedInfo.phoneNumber;
                memberEmail.value = updatedInfo.email;
                memberAddress.value = updatedInfo.address;

                sessionStorage.setItem('memberNickname', updatedInfo.nickname);
                sessionStorage.setItem('memberPhoneNumber', updatedInfo.phoneNumber);
                sessionStorage.setItem('memberEmail', updatedInfo.email);
                sessionStorage.setItem('memberAddress', updatedInfo.address);

                // Swal 알림 - 수정 성공
                Swal.fire({
                    icon: 'success',
                    title: '수정 완료',
                    text: '회원 정보가 성공적으로 수정되었습니다.',
                    customClass: {
                        title: 'custom-swal-title',
                        text: 'custom-swal-text',
                        confirmButton: 'custom-swal-button',
                    },
                    buttonsStyling: false,
                });
            } catch (error) {
                console.error('회원 정보 수정 중 오류가 발생했습니다:', error);

                // Swal 알림 - 수정 실패
                Swal.fire({
                    icon: 'error',
                    title: '수정 실패',
                    text: '회원 정보 수정에 실패했습니다. 다시 시도해주세요.',
                    customClass: {
                        title: 'custom-swal-title',
                        text: 'custom-swal-text',
                        confirmButton: 'custom-swal-button',
                    },
                    buttonsStyling: false,
                });
            }
        }
    }

    const isLoggedIn = computed(() => !!memberToken.value);

    loadMemberToken();

    return {
        memberToken,
        memberId,
        memberName,
        memberNickname,
        memberGender,
        memberBirthDate,
        memberAge,
        memberEmail,
        memberPhoneNumber,
        memberAddress,
        memberRole,
        isLoggedIn,
        setMemberToken,
        clearMemberToken,
        login,
        modifyMember,
    };
});
