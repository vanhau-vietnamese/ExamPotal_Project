import { Link } from 'react-router-dom';
import { BodyExercise } from '~/components';

export default function ExamList() {
  return (
    <div className="flex">
      <div className="bg-white m-3 rounded-lg w-5/6 h-full">
        <div className="flex flex-wrap justify-center grid-cols-3">
          <BodyExercise></BodyExercise>
          <BodyExercise></BodyExercise>
          <BodyExercise></BodyExercise>
          <BodyExercise></BodyExercise>
          <BodyExercise></BodyExercise>
          <BodyExercise></BodyExercise>
          <BodyExercise></BodyExercise>
          <BodyExercise></BodyExercise>
        </div>
      </div>

      <div className=" mt-9 rounded-lg w-1/6">
        <Link className="p-3 mt-1 rounded-xl justify-center font-bold bg-green-500 hover:bg-green-700 text-white ">
          Tạo bài tập
        </Link>
      </div>
    </div>
  );
}
