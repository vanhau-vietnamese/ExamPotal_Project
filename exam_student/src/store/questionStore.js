import { create } from 'zustand';

export const useQuestionStore = create((set) => ({
  isEditing: false,
  questionTypes: [],
  questionList: [],
  targetQuestion: null,
  setQuestionType: (questionTypes) => set({ questionTypes }),
  setIsEditing: (isEditing) => set({ isEditing }),
  setQuestionList: (questionList) => set({ questionList }),
  addNewQuestion: (newQuestion) =>
    set((state) => ({ questionList: [...state.questionList, newQuestion] })),
  setTargetQuestion: (targetQuestion) => set({ targetQuestion }),
  updateQuestion: (targetQuestion) => set((state)=>{ 
    const indexQues = state.questionList.findIndex((question)=>{
      return question.id === targetQuestion.id
    });
    if(indexQues === -1){
      return state;
    }
    const cloneList = [...state.questionList];
    cloneList[indexQues] = targetQuestion;
    return { questionList: cloneList }
  }),
}));