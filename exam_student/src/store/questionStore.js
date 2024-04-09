import { create } from 'zustand';

export const useQuestionStore = create((set) => ({
  modal: null,
  questionTypes: [],
  questionList: [],
  targetQuestion: null,
  setQuestionType: (questionTypes) => set({ questionTypes }),
  openModal: (type) => set({ modal: type }),
  setQuestionList: (questionList) => set({ questionList }),
  addNewQuestion: (newQuestion) =>
    set((state) => ({ questionList: [...state.questionList, newQuestion] })),
  setTargetQuestion: (targetQuestion) => set({ targetQuestion }),
  updateQuestion: (targetQuestion) =>
    set((state) => {
      const indexQues = state.questionList.findIndex((question) => {
        return question.id === targetQuestion.id;
      });
      if (indexQues === -1) {
        return state;
      }
      const cloneList = [...state.questionList];
      cloneList[indexQues] = targetQuestion;
      return { questionList: cloneList };
    }),
  removeQuestion: (questionId) =>
    set((state) => ({
      questionList: state.questionList.filter((question) => question.id !== questionId),
    })),
}));
