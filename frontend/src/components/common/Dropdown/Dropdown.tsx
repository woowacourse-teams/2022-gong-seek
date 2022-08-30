import { useNavigate } from 'react-router-dom';

import * as S from '@/components/common/Dropdown/Dropdown.styles';

const Dropdown = ({ onClickCloseDropdown }: { onClickCloseDropdown: () => void }) => {
	const navigate = useNavigate();

	const onLogOutClick = () => {
		if (window.confirm('정말로 로그아웃을 하시겠습니까?')) {
			localStorage.removeItem('accessToken');
			window.location.href = '/';
			return;
		}
	};

	return (
		<S.Container>
			<S.DropdownItem onClick={() => navigate('/my-page')}>마이페이지</S.DropdownItem>
			<S.DropdownItem onClick={onLogOutClick}>로그아웃</S.DropdownItem>
		</S.Container>
	);
};

export default Dropdown;
