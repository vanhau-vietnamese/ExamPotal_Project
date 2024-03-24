import { Link } from 'react-router-dom';

export default function FeatureQuestion() {
  return (
    <div className="flex m-3">
      <Link className="font-medium m-3 text-green-600  hover:underline">Sửa</Link>
      <p className="m-3"> | </p>
      <Link className="font-medium m-3 text-red-600  hover:underline">Xóa</Link>
    </div>
  );
}
