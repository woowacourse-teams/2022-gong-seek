import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';
import { IoIosArrowBack } from 'react-icons/io';

const showSlider = keyframes`
	0%{
		transform: translate3d(500px, 0,0);
	}100%{
		transform: translate3d(0,0,0);
	}
`;

export const Container = styled.section`
	display: flex;
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background-color: ${({ theme }) => theme.colors.GRAY_500};
	z-index: ${({ theme }) => theme.zIndex.ARTICLE_BACKGROUND_CONTENT};
`;

export const MenuBox = styled.div`
	display: flex;
	position: fixed;
	justify-content: flex-start;
	flex-direction: column;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_018};

	top: 0;
	right: 0;
	width: 70%;
	height: 100vh;
	background-color: ${({ theme }) => theme.colors.WHITE};
	z-index: ${({ theme }) => theme.zIndex.ARTICLE_POPULAR_CONTENT};
	padding-top: ${({ theme }) => theme.size.SIZE_030};
	border-radius: ${({ theme }) => theme.size.SIZE_010};

	animation: ${showSlider} 0.2s ease-in-out;
`;

export const Header = styled.header`
	display: flex;
	justify-content: flex-start;
	width: 80%;
	margin-bottom: ${({ theme }) => theme.size.SIZE_050};
`;

export const BackButtonBox = styled.button`
	border-style: none;
	background-color: transparent;
	margin-left: -${({ theme }) => theme.size.SIZE_010};
	cursor: pointer;
`;

export const BackButton = styled(IoIosArrowBack)`
	font-size: ${({ theme }) => theme.size.SIZE_024};
	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
`;

export const LinkBox = styled.div`
	width: 80%;
	display: flex;
	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_040};
`;

export const LinkItem = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_016};
	cursor: pointer;
	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
		font-weight: 600;
	}
`;
