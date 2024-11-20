import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';

const REST_API_URL = `http://localhost:8080/api-member`;

export const useMemberStore = defineStore('member', () => {
    const memberToken = ref(null);

    const setMemberToken = (token) => {
        memberToken.value = token;
        sessionStorage.setItem('memberToken', token);
    };

    const clearMemberToken = () => {
        memberToken.value = null;
        sessionStorage.removeItem('memberToken');
    };

    const loadMemberToken = () => {
        const token = sessionStorage.getItem('memberToken');
        if (token) {
            memberToken.value = token;
        }
    };

    const login = (id, password) => {
        axios.post(`${REST_API_URL}/login`, {
            id,
            password
        })
        .then((response) => {
            const token = response.headers['authorization'];
            if (token) {
                setMemberToken(token);
                router.push({ name: 'home' }); // 로그인 성공 시 홈으로 이동
            } else {
                console.error('로그인에 실패했습니다. 토큰이 없습니다.');
            }
        })
        .catch((error) => {
            console.error('로그인 요청 중 오류가 발생했습니다:', error);
        });
    };

    loadMemberToken();

    return { memberToken, setMemberToken, clearMemberToken, login };
});
