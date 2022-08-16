import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import * as S from '@/components/layout/TabBar/TabBar.styles';
import { menuSliderState } from '@/store/menuSliderState';

const TabBar = () => {
	const navigate = useNavigate();
	const [sliderState, setSliderState] = useRecoilState(menuSliderState);

	return (
		<>
			<S.Section>
				<S.PostingLink
					onClick={() => {
						navigate('/category');
					}}
				/>
				<S.UserCircleLink
					onClick={() => {
						navigate('/my-page');
					}}
				/>
				<S.MenuLink
					onClick={() => {
						setSliderState({ isOpen: true });
					}}
				/>
			</S.Section>
		</>
	);
};

export default TabBar;
