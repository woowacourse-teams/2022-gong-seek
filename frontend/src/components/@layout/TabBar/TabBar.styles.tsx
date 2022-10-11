import { AiFillEdit, AiOutlineMenu } from 'react-icons/ai';
import { Link } from 'react-router-dom';

import styled from '@emotion/styled';

export const Section = styled.section`
	display: flex;
	position: fixed;

	bottom: 0;

	justify-content: space-between;
	align-items: center;

	width: 100vw;
	height: ${({ theme }) => theme.size.SIZE_056};

	border-radius: ${({ theme }) => theme.size.SIZE_010} ${({ theme }) => theme.size.SIZE_010} 0 0;

	box-shadow: 0px -4px 15px ${({ theme }) => theme.boxShadows.secondary};
	background-color: ${({ theme }) => theme.colors.WHITE};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_SMALL}) {
		display: none;
		visibility: hidden;
	}
`;

export const PostingLink = styled(AiFillEdit)`
	font-size: ${({ theme }) => theme.size.SIZE_026};

	color: ${({ theme }) => theme.colors.BLACK_500};

	margin: 16px 0 16px 16px;

	cursor: pointer;

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;

export const MyPageLink = styled(Link)``;

export const MenuLink = styled(AiOutlineMenu)`
	font-size: ${({ theme }) => theme.size.SIZE_024};

	color: ${({ theme }) => theme.colors.BLACK_500};

	margin: 16px 16px 16px 0;

	cursor: pointer;

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;
