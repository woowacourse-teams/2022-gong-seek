import * as S from '@/components/layout/TabBar/TabBar.style';
import { useNavigate } from 'react-router-dom';

const TabBar = () => {
	const navigate = useNavigate();
	return (
		<S.Section>
			<S.PostingLink
				onClick={() => {
					navigate('/category');
				}}
			/>
			<S.UserCircleLink />
			<S.MenuLink />
		</S.Section>
	);
};

export default TabBar;
