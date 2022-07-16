import styled from '@emotion/styled';
import { keyframes } from '@emotion/react';

const showSlider = keyframes`
    0%{
        transform: translate3d(0,500px,0);
    }
    100%{
        transform: translate3d(0,0,0);
    }
`;

export const Container = styled.section`
	display: flex;
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	justify-content: center;
	text-align: center;
	z-index: 110;
`;

export const CommentContainer = styled.div`
	display: flex;
	flex-direction: column;
	position: fixed;
	bottom: 0;
	left: 0;
	width: 100%;
	height: 80vh;
	justify-content: center;
	align-items: center;
	background-color: ${({ theme }) => theme.colors.WHITE};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
	animation: ${showSlider} 0.2s ease-in-out;
	z-index: 110;
`;

export const CommentTitle = styled.h2`
	display: block;
	width: 80%;
	text-align: start;
	font-size: ${({ theme }) => theme.size.SIZE_020};
	margin-bottom: ${({ theme }) => theme.size.SIZE_016};
`;

export const CommentContent = styled.textarea`
	width: 80%;
	height: 70%;
	outline: none;
	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.BLACK_600};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
`;

export const CommentPostButton = styled.button`
	width: 80%;
	height: fit-content;
	padding: ${({ theme }) => theme.size.SIZE_004};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};
	color: ${({ theme }) => theme.colors.WHITE};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border: none;
	margin-top: ${({ theme }) => theme.size.SIZE_020};
	cursor: pointer;

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;
