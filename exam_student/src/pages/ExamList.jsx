import { useState } from 'react';
import { Backdrop, BodyExercise, Button } from '~/components';
import FormCreateExam from './FormCreateExam';

export default function ExamList() {
  const [isCreatingExam, setIsCreatingExam] = useState(false);

  const handleCreateExam = () => {
    setIsCreatingExam(true);
  };
  return (
    <div className="flex">
      <div className="bg-white m-3 rounded-lg w-5/6 h-full">
        <div className="flex flex-wrap justify-center grid-cols-3">
          <BodyExercise></BodyExercise>
        </div>
      </div>

      <div className=" mt-9 rounded-lg w-1/6">
        <Button
          className="p-3 mt-1 rounded-xl justify-center font-bold bg-green-500 hover:bg-green-700 text-white "
          onClick={handleCreateExam}
        >
          Tạo bài tập
        </Button>
      </div>
      {isCreatingExam && (
        <Backdrop opacity={0.35} className="overflow-auto">
          <FormCreateExam onClose={() => setIsCreatingExam(false)}></FormCreateExam>
        </Backdrop>
      )}
    </div>
  );
}
