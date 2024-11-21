import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router'

const REST_API_URL = `http://localhost:8080/api-board`;

export const useBoardStore = defineStore('board', () => {
    
    const boardList = ref([])
    const board = ref({})

    const getBoardList = () => {
      axios.get(`${REST_API_URL}/list`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken')
        }
      })
      .then((response) => {
        boardList.value = response.data.list
      })
      .catch((err) => {
        console.log(err)
      })
    }
    
    const getBoardDetail = async (boardNo) => {
      await axios.get(`${REST_API_URL}/${boardNo}`, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken')
        }
      })
      .then((response) => {
        board.value = response.data
      })
      .catch((err) => {
        alert(err.data)
      })
    }

    const writeBoard = (formData) => {
      axios.post(`${REST_API_URL}/write`, formData, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
          'Content-Type': 'multipart/form-data'
        }
      })
      .then((response) => {
        alert(response.data);
        router.push({name: 'community'});
      })
      .catch((err) => {
        console.error(err);
        alert('글을 등록하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      });
    }

    const modifyBoard = (boardNo, formData) => {
      axios.put(`${REST_API_URL}/modify/${boardNo}`, formData, {
        headers: {
          'Authorization': sessionStorage.getItem('memberToken'),
          'Content-Type': 'multipart/form-data'
        }
      })
      .then((response) => {
        alert(response.data);
        router.push({name: 'community'});
      })
      .catch((err) => {
        console.error(err);
        alert('글을 수정하는 중 오류가 발생했습니다. 다시 시도해 주세요.');
      });
    }

    return { boardList, getBoardList, writeBoard, modifyBoard, getBoardDetail, board };
});