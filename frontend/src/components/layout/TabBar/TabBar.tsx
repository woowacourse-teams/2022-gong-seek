import MenuSlider from '@/components/common/MenuSlider/MenuSlider';
import * as S from '@/components/layout/TabBar/TabBar.styles';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const TabBar = () => {
	const navigate = useNavigate();
	const [isMenuSliderOpen, setIsMenuSliderOpen] = useState(false);

	return (
		<>
			<S.Section>
				<S.PostingLink
					onClick={() => {
						navigate('/category');
					}}
				/>
				<S.UserCircleLink />
				<S.MenuLink
					onClick={() => {
						setIsMenuSliderOpen(true);
					}}
				/>
			</S.Section>
			{isMenuSliderOpen && <MenuSlider closeSlider={() => setIsMenuSliderOpen(false)} />}
		</>
	);
};

export default TabBar;
