import { deleteQuestion } from '~/apis';
import { Button } from '~/components';
import { useQuestionStore } from '~/store';
import { toast } from 'react-toastify';

export default function FormSubmitDelete() {
  const { setIsDeleting, targetQuestion, removeQuestion } = useQuestionStore((state) => state);

  const handleDeleteQuestion = async () => {
    try {
      const response = await deleteQuestion(targetQuestion.id);
      if (response) {
        removeQuestion(response);
        setIsDeleting(false);
        toast.success('Xóa câu hỏi thành công', { toastId: 'delete_question' });
      }
    } catch (error) {
      toast.error(error);
    }
  };

  return (
    <div className="flex items-center justify-center pt-6">
      <div className="container mx-auto p-4 bg-slate-100 rounded-md h-[150px] w-[300px]">
        <h3 className="mb-5">Xác nhận xóa!</h3>
        <div className="flex">
          <Button
            onClick={() => handleDeleteQuestion()}
            className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 w-32 h-12 text-sm rounded m-1"
          >
            Xóa
          </Button>
          <Button
            onClick={() => {
              setIsDeleting(false);
            }}
            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 w-32 h-12 text-sm rounded m-1"
          >
            Thoát
          </Button>
        </div>
      </div>
    </div>
  );
}
