import styled from '@emotion/styled';
import { FaPencilAlt, FaUser } from 'react-icons/fa';
import { HiMenu } from 'react-icons/hi';

export const Section = styled.section`
	width: 100%;
	height: fit-content;
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 1rem;
	box-shadow: 0px -4px 15px ${({ theme }) => theme.boxShadows.secondary};
	border-radius: ${({ theme }) => theme.fonts.SIZE_010} ${({ theme }) => theme.fonts.SIZE_010} 0 0;
`;

export const PostingLink = styled(FaPencilAlt)`
	font-size: ${({ theme }) => theme.fonts.SIZE_022};
	color: ${({ theme }) => theme.colors.BLACK_500};
	cursor: pointer;

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;

export const UserCircleLink = styled(FaUser)`
	font-size: ${({ theme }) => theme.fonts.SIZE_022};
	color: ${({ theme }) => theme.colors.BLACK_500};
	cursor: pointer;
	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;

export const MenuLink = styled(HiMenu)`
	font-size: ${({ theme }) => theme.fonts.SIZE_024};
	color: ${({ theme }) => theme.colors.BLACK_500};
	cursor: pointer;
	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;

const TabBar = () => (
	<Section>
		<PostingLink />
		<UserCircleLink />
		<MenuLink />
	</Section>
);

export default TabBar;
