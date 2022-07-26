import { FaPencilAlt, FaUser } from 'react-icons/fa';
import { HiMenu } from 'react-icons/hi';

import styled from '@emotion/styled';

export const Section = styled.section`
	display: flex;
	position: sticky;

	bottom: 0;

	justify-content: space-between;
	align-items: center;

	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_056};

	border-radius: ${({ theme }) => theme.size.SIZE_010} ${({ theme }) => theme.size.SIZE_010} 0 0;

	box-shadow: 0px -4px 15px ${({ theme }) => theme.boxShadows.secondary};
	background-color: ${({ theme }) => theme.colors.WHITE};
`;

export const PostingLink = styled(FaPencilAlt)`
	font-size: ${({ theme }) => theme.size.SIZE_022};

	color: ${({ theme }) => theme.colors.BLACK_500};

	margin: 16px 0 16px 16px;

	cursor: pointer;

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;

export const UserCircleLink = styled(FaUser)`
	font-size: ${({ theme }) => theme.size.SIZE_022};

	color: ${({ theme }) => theme.colors.BLACK_500};

	cursor: pointer;

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;

export const MenuLink = styled(HiMenu)`
	font-size: ${({ theme }) => theme.size.SIZE_024};

	color: ${({ theme }) => theme.colors.BLACK_500};

	margin: 16px 16px 16px 0;

	cursor: pointer;

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;
