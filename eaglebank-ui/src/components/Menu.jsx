import { useParams, NavLink } from 'react-router-dom';
import './FormBox.css';

export default function Menu() {
  const { userId } = useParams();
  return (
    <nav className="menu-nav" aria-label="Main navigation">
      <ul className="menu-nav__list">
        <li>
          <NavLink
            to={`/user/${userId}`}
            className={({ isActive }) =>
              "menu-nav__link" + (isActive ? " menu-nav__link--active" : "")
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
              "menu-nav__link" + (isActive ? " menu-nav__link--active" : "")
            }
          >
            Edit User
          </NavLink>
        </li>
        <li>
          <NavLink
            to={`/user/${userId}/accounts`}
            className={({ isActive }) =>
              "menu-nav__link" + (isActive ? " menu-nav__link--active" : "")
            }
          >
            Accounts
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}
