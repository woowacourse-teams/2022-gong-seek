import { css, keyframes } from '@emotion/react';
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

	z-index: ${({ theme }) => theme.zIndex.COMMON_INPUT_MODAL};
`;

export const CommentContainer = styled.div`
	display: flex;
	position: fixed;

	bottom: 0;
	left: 0;

	flex-direction: column;
	align-items: center;

	width: 100%;
	height: 80vh;

	overflow: scroll;

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};

		background-color: ${theme.colors.WHITE};

		z-index: ${theme.zIndex.COMMON_INPUT_MODAL};

		animation: ${showSlider} 0.2s ease-in-out;

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: 70vw;
			height: 60vh;
			margin: 0 auto;
			left: 0;
			right: 0;

			::-webkit-scrollbar {
				display: none;
			}
		}
	`}
`;

export const CommentTitle = styled.h2`
	display: block;

	width: 100%;

	text-align: start;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_020};

		margin: ${theme.size.SIZE_016} 0 ${theme.size.SIZE_016} ${theme.size.SIZE_026};

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			margin-top: ${theme.size.SIZE_020};
		}
	`}
`;

export const CommentContentBox = styled.div`
	width: 90%;
	height: 80%;
	overflow: hidden;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		height: 40vh;
	}
`;

export const CommentPostButton = styled.button`
	width: 80%;
	height: fit-content;
	border: none;

	cursor: pointer;

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};

		background-color: ${theme.colors.PURPLE_500};
		color: ${theme.colors.WHITE};

		padding: ${theme.size.SIZE_004};

		&:hover,
		&:active {
			background-color: ${theme.colors.PURPLE_400};
		}

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: ${theme.size.SIZE_160};
		}
	`}
`;

export const SubmitBox = styled.div`
	display: flex;

	align-items: center;
	justify-content: end;

	width: 80%;

	${({ theme }) => css`
		gap: ${theme.size.SIZE_022};
		margin-top: ${theme.size.SIZE_020};
		margin-bottom: ${theme.size.SIZE_050};

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: 100%;
			display: flex;
			justify-content: flex-end;
			gap: ${theme.size.SIZE_016};

			margin: ${theme.size.SIZE_040} ${theme.size.SIZE_026} ${theme.size.SIZE_020} 0;
		}
	`}
`;
