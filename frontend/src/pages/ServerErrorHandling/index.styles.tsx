import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	position: absolute;
	flex-direction: column;
	align-items: center;
	justify-content: center;

	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	width: 100vw;
	height: 100%;

	background-color: ${({ theme }) => theme.colors.WHITE};

	z-index: ${({ theme }) => theme.zIndex.SERVER_ERROR};
	overflow: hidden;
	touch-action: none;
`;

export const LogoImg = styled.img`
	width: ${({ theme }) => theme.size.SIZE_090};
	height: ${({ theme }) => theme.size.SIZE_090};

	margin-bottom: ${({ theme }) => theme.size.SIZE_056};
`;

export const Message = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_020};
	font-weight: 400;
	line-height: ${({ theme }) => theme.size.SIZE_040};
	text-align: center;
	word-break: keep-all;
`;
