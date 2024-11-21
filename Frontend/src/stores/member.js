import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';

const REST_API_URL = `http://localhost:8080/api-member`;
// const REST_API_URL = `http://192.168.210.83:8080/api-member`;

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

    const login = (id, password) => {
        return axios.post(`${REST_API_URL}/login`, { id, password })
            .then((response) => {
                const token = response.headers['authorization'];
                if (token) {
                    setMemberToken(token);
                    loadMemberInfo(id); // 로그인 성공 후 사용자 정보 로드
                    router.push({ name: 'home' }); // 홈으로 이동
                } else {
                    console.error('로그인에 실패했습니다. 토큰이 없습니다.');
                    return Promise.reject('로그인에 실패했습니다. 토큰이 없습니다.');
                }
            })
            .catch((error) => {
                console.error('로그인 요청 중 오류가 발생했습니다:', error);
                return Promise.reject(error);
            });
    };


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
        setMemberToken,
        clearMemberToken,
        login,
    };
});
