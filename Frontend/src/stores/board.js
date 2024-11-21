import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';
import Swal from 'sweetalert2';
import '@/assets/swal_custom.css';  // 커스텀 CSS를 사용한다는 가정하에 추가

// const REST_API_URL = `http://192.168.210.83:8080/api-board`;
const REST_API_URL = `http://localhost:8080/api-board`;

export const useBoardStore = defineStore('board', () => {
    
    const boardList = ref([]);
    const board = ref({});

    const getBoardList = () => {
        axios.get(`${REST_API_URL}/list`, {
            headers: {
                'Authorization': sessionStorage.getItem('memberToken'),
            }
        })
        .then((response) => {
            boardList.value = response.data;
        })
        .catch((err) => {
            console.error(err);
            Swal.fire({
                icon: 'error',
                title: '오류',
                text: '게시판 목록을 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.',
                customClass: {
                    title: 'custom-swal-title',
                    text: 'custom-swal-text',
                    confirmButton: 'custom-swal-button',
                },
                buttonsStyling: false,
            });
        });
    };
    
    const getBoardDetail = async (boardNo) => {
        await axios.get(`${REST_API_URL}/${boardNo}`, {
            headers: {
                'Authorization': sessionStorage.getItem('memberToken'),
            }
        })
        .then((response) => {
            board.value = response.data;
        })
        .catch((err) => {
            console.error(err);
            Swal.fire({
                icon: 'error',
                title: '오류',
                text: '게시판 상세 정보를 불러오는 중 오류가 발생했습니다.',
                customClass: {
                    title: 'custom-swal-title',
                    text: 'custom-swal-text',
                    confirmButton: 'custom-swal-button',
                },
                buttonsStyling: false,
            });
        });
    };

    const writeBoard = (formData) => {
        return axios.post(`${REST_API_URL}/write`, formData, {
            headers: {
                'Authorization': sessionStorage.getItem('memberToken'),
                'Content-Type': 'multipart/form-data',
            }
        })
        .then((response) => {
            Swal.fire({
                icon: 'success',
                title: '등록 완료',
                text: '게시글이 성공적으로 등록되었습니다.',
                customClass: {
                    title: 'custom-swal-title',
                    text: 'custom-swal-text',
                    confirmButton: 'custom-swal-button',
                },
                buttonsStyling: false,
            });
            return response; // 성공 시 response 반환
        })
        .catch((err) => {
            console.error(err);
            Swal.fire({
                icon: 'error',
                title: '등록 오류',
                text: '글을 등록하는 중 오류가 발생했습니다. 다시 시도해 주세요.',
                customClass: {
                    title: 'custom-swal-title',
                    text: 'custom-swal-text',
                    confirmButton: 'custom-swal-button',
                },
                buttonsStyling: false,
            });
            throw err; // 오류 발생 시 예외 던지기
        });
    };

    const modifyBoard = (boardNo, formData) => {
        return axios.put(`${REST_API_URL}/modify/${boardNo}`, formData, {
            headers: {
                'Authorization': sessionStorage.getItem('memberToken'),
                'Content-Type': 'multipart/form-data',
            }
        })
        .then((response) => {
            Swal.fire({
                icon: 'success',
                title: '수정 완료',
                text: '게시글이 성공적으로 수정되었습니다.',
                customClass: {
                    title: 'custom-swal-title',
                    text: 'custom-swal-text',
                    confirmButton: 'custom-swal-button',
                },
                buttonsStyling: false,
            });
            return response; // 성공 시 response 반환
        })
        .catch((err) => {
            console.error(err);
            Swal.fire({
                icon: 'error',
                title: '수정 오류',
                text: '글을 수정하는 중 오류가 발생했습니다. 다시 시도해 주세요.',
                customClass: {
                    title: 'custom-swal-title',
                    text: 'custom-swal-text',
                    confirmButton: 'custom-swal-button',
                },
                buttonsStyling: false,
            });
            throw err; // 오류 발생 시 예외 던지기
        });
    };

    return { boardList, getBoardList, writeBoard, modifyBoard, getBoardDetail, board };
});
