import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';
import Swal from 'sweetalert2';
import '@/assets/swal_custom.css';

const REST_API_URL = `http://192.168.210.83:8080/api-board`;
// const REST_API_URL = `http://localhost:8080/api-board`;

export const useBoardStore = defineStore('board', () => {

    const boardList = ref([]);
    const board = ref({});
    const isLiked = ref(false);

    const getBoardList = async () => {
        await axios.get(`${REST_API_URL}/list`, {
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

    const writeBoard = async (formData) => {
        return await axios.post(`${REST_API_URL}/write`, formData, {
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

    const modifyBoard = async (boardNo, formData) => {
        return await axios.put(`${REST_API_URL}/modify/${boardNo}`, formData, {
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

    const deleteBoard = async (boardNo) => {
        return await axios.delete(`${REST_API_URL}/${boardNo}`, {
            headers: {
                'Authorization': sessionStorage.getItem('memberToken'),
            }
        })
            .then((response) => {
                Swal.fire({
                    icon: 'success',
                    title: '삭제 완료',
                    text: '게시글이 성공적으로 삭제되었습니다.',
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
                    title: '삭제 오류',
                    text: '게시글을 삭제하는 중 오류가 발생했습니다. 다시 시도해 주세요.',
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

    const addComment = async (boardNo, commentData) => {
        return await axios.post(`${REST_API_URL}/comment/${boardNo}`, commentData, {
            headers: {
                'Authorization': sessionStorage.getItem('memberToken'),
                'Content-Type': 'application/json',
            }
        })
            .then((response) => {
                Swal.fire({
                    icon: 'success',
                    title: '댓글 등록 완료',
                    text: '댓글이 성공적으로 등록되었습니다.',
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
                    title: '댓글 등록 오류',
                    text: '댓글을 등록하는 중 오류가 발생했습니다. 다시 시도해 주세요.',
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

    const getComments = async (boardNo) => {
        return await axios.get(`${REST_API_URL}/comment/${boardNo}`, {
            headers: {
                'Authorization': sessionStorage.getItem('memberToken'),
            }
        })
            .then((response) => {
                board.value.comments = response.data;
                console.log(board.value.comments)
            })
            .catch((err) => {
                console.error(err);
                Swal.fire({
                    icon: 'error',
                    title: '댓글 불러오기 오류',
                    text: '댓글을 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.',
                    customClass: {
                        title: 'custom-swal-title',
                        text: 'custom-swal-text',
                        confirmButton: 'custom-swal-button',
                    },
                    buttonsStyling: false,
                });
            });
    };

    const likePlus = async (boardNo, id) => {
        await axios.put(`${REST_API_URL}/like/plus/${boardNo}/${id}`, {
            headers: {
                'Authorization': sessionStorage.getItem('memberToken'),
            }
        })
            .then((response) => {
                console.log(response.data)
            })
            .catch((err) => {
                console.error(err);
                Swal.fire({
                    icon: 'error',
                    title: '좋아요 오류',
                    text: '오류가 발생했습니다. 다시 시도해 주세요.',
                    customClass: {
                        title: 'custom-swal-title',
                        text: 'custom-swal-text',
                        confirmButton: 'custom-swal-button',
                    },
                    buttonsStyling: false,
                });
            })
    }

    const likeMinus = async (boardNo, id) => {
        await axios.put(`${REST_API_URL}/like/minus/${boardNo}/${id}`, {
            headers: {
                'Authorization': sessionStorage.getItem('memberToken'),
            }
        })
            .then((response) => {
                console.log(response.data)
            })
            .catch((err) => {
                console.error(err);
                Swal.fire({
                    icon: 'error',
                    title: '좋아요 취소 오류',
                    text: '오류가 발생했습니다. 다시 시도해 주세요.',
                    customClass: {
                        title: 'custom-swal-title',
                        text: 'custom-swal-text',
                        confirmButton: 'custom-swal-button',
                    },
                    buttonsStyling: false,
                });
            })
    }

    const likeStatus = async (boardNo, id) => {
        try {
            const response = await axios.get(`${REST_API_URL}/like/status/${boardNo}/${id}`, {
                headers: {
                    'Authorization': sessionStorage.getItem("memberToken"),
                },
            })
            isLiked.value = response.data;
        } catch (error) {
            console.error(error)
        }
    }

    const deleteComment = async (commentNo) => {
        try {
          await axios.delete(`${REST_API_URL}/comment/${commentNo}`, {
            headers: {
              Authorization: sessionStorage.getItem("memberToken"),
            },
          });
          Swal.fire({
            icon: "success",
            title: "삭제 완료",
            text: "댓글이 삭제되었습니다.",
          });
        } catch (error) {
          console.error("댓글 삭제 중 오류 발생:", error);
          Swal.fire({
            icon: "error",
            title: "삭제 실패",
            text: "댓글 삭제 중 문제가 발생했습니다. 다시 시도해주세요.",
          });
          throw error; // 필요 시 상위에서 에러 처리 가능
        }
      };
    
      

    return { boardList, getBoardList, writeBoard, modifyBoard, getBoardDetail, deleteBoard, board, addComment, getComments, likePlus, likeMinus, deleteComment, likeStatus };
});
