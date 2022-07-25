import styled from '@emotion/styled';
import { FaPencilAlt, FaUser } from 'react-icons/fa';
import { HiMenu } from 'react-icons/hi';

export const Section = styled.section`
	position: sticky;
	bottom: 0;
	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_056};
	display: flex;
	justify-content: space-between;
	align-items: center;
	box-shadow: 0px -4px 15px ${({ theme }) => theme.boxShadows.secondary};
	border-radius: ${({ theme }) => theme.size.SIZE_010} ${({ theme }) => theme.size.SIZE_010} 0 0;
	background-color: ${({ theme }) => theme.colors.WHITE};
`;

export const PostingLink = styled(FaPencilAlt)`
	font-size: ${({ theme }) => theme.size.SIZE_022};
	color: ${({ theme }) => theme.colors.BLACK_500};
	cursor: pointer;
	margin: 16px 0 16px 16px;
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
	cursor: pointer;
	margin: 16px 16px 16px 0;
	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;
