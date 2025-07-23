import { useParams, NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import './FormBox.css';

export default function Menu() {
  const { userId } = useParams();
  const navigate = useNavigate();
  const { setJwt } = useAuth();

  function handleLogout() {
    setJwt('');
    navigate('/');
  }

  return (
    <div>
      <div style={{
        display: "flex",
        justifyContent: "flex-end",
        alignItems: "center",
        width: "100%",
        marginBottom: "0.5rem"
      }}>
        <button
          className="menu__logout"
          style={{ minWidth: 100 }}
          onClick={handleLogout}
        >
          Logout
        </button>
      </div>
      <nav className="menu-nav" aria-label="Main navigation" style={{ marginBottom: "2rem" }}>
        <ul
          className="menu-nav__list"
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            gap: "2rem",
            listStyle: "none",
            padding: 0,
            margin: 0
          }}
        >
          <li>
            <NavLink
              to={`/user/${userId}`}
              className={({ isActive }) =>
                'menu-nav__link' + (isActive ? ' menu-nav__link--active' : '')
              }
              end
            >
              View User
            </NavLink>
          </li>
          <li>
            <NavLink
              to={`/user/${userId}/update`}
              className={({ isActive }) =>
                'menu-nav__link' + (isActive ? ' menu-nav__link--active' : '')
              }
            >
              Edit User
            </NavLink>
          </li>
          <li>
            <NavLink
              to={`/user/${userId}/accounts`}
              className={({ isActive }) =>
                'menu-nav__link' + (isActive ? ' menu-nav__link--active' : '')
              }
            >
              Accounts
            </NavLink>
          </li>
        </ul>
      </nav>
    </div>
  );
}
