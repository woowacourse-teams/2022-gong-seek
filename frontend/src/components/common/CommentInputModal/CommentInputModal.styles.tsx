import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

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
	position: fixed;

	bottom: 0;
	left: 0;

	flex-direction: column;
	justify-content: center;
	align-items: center;

	width: 100%;
	height: 80vh;

	border-radius: ${({ theme }) => theme.size.SIZE_010};

	background-color: ${({ theme }) => theme.colors.WHITE};

	z-index: 110;

	animation: ${showSlider} 0.2s ease-in-out;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 60vw;
		height: 50vh;
		margin: 0 auto;
		left: 0;
		right: 0;
	}
`;

export const CommentTitle = styled.h2`
	display: block;

	width: 80%;

	text-align: start;
	font-size: ${({ theme }) => theme.size.SIZE_020};

	margin-bottom: ${({ theme }) => theme.size.SIZE_016};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		margin-top: ${({ theme }) => theme.size.SIZE_020};
	}
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

	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border: none;

	background-color: ${({ theme }) => theme.colors.PURPLE_500};
	color: ${({ theme }) => theme.colors.WHITE};

	padding: ${({ theme }) => theme.size.SIZE_004};

	cursor: pointer;

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: ${({ theme }) => theme.size.SIZE_160};
	}
`;

export const SubmitBox = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_022};

	align-items: center;
	justify-content: end;
	margin-top: ${({ theme }) => theme.size.SIZE_050};
	width: 80%;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 100%;
		display: flex;
		justify-content: flex-end;
		padding-right: ${({ theme }) => theme.size.SIZE_260};
		gap: ${({ theme }) => theme.size.SIZE_016};

		margin-bottom: ${({ theme }) => theme.size.SIZE_020};
	}
`;
