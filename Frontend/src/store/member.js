import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/api-member`

export const useMemberStore = defineStore('member', () => {
    const memberToken = ref(null);

    const setMemberToken = (token) => {
        memberToken.value = token;
        localStorage.setItem('memberToken', token);
    };

    const clearMemberToken = () => {
        memberToken.value = null;
        localStorage.removeItem('memberToken');
    };

    const loadMemberToken = () => {
        const token = localStorage.getItem('memberToken');
        if (token) {
            memberToken.value = token;
        }
    };

    const setTokenFromResponse = (response) => {
        const token = response.headers['authorization'];
        if (token) {
            setMemberToken(token);
        }
    };

    loadMemberToken();

    return { memberToken, setMemberToken, clearMemberToken, setTokenFromResponse };
});
