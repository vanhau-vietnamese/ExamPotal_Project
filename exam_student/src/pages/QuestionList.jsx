import { useState } from 'react';
import { Backdrop, Button, QuestionItem, TypeLanguges } from '~/components';
import FormCreateQuestion from './FormCreateQuestion';

export default function QuestionList() {
  const [isCreatingQuestion, setIsCreatingQuestion] = useState(false);

  const handleCreateQuestion = () => {
    setIsCreatingQuestion(true);
  };
  return (
    <div>
      <TypeLanguges></TypeLanguges>
      <div className="flex">
        <div className="bg-white m-3 rounded-lg w-5/6 h-full">
          <QuestionItem></QuestionItem>
        </div>

        <div className="mr-3 mt-9 rounded-lg w-1/6 h-full">
          <Button
            className="p-3 mt-1 rounded-xl justify-center font-bold bg-green-500 hover:bg-green-700 text-white "
            onClick={handleCreateQuestion}
          >
            Tạo câu hỏi
          </Button>
        </div>

        {isCreatingQuestion && (
          <Backdrop opacity={0.35} className="overflow-auto">
            <FormCreateQuestion onClose={() => setIsCreatingQuestion(false)}></FormCreateQuestion>
          </Backdrop>
        )}
      </div>
    </div>
  );
}
