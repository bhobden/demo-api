import { useParams } from 'react-router-dom';

export default function Menu() {
  const { userId } = useParams();
  return (
    <div>
        <a href={`/user/${userId}`}>View User</a> | <a href={`/user/${userId}/update`}>Edit User</a> | <a href={`/user/${userId}/accounts`}>Accounts</a>
    </div>
  );
}
