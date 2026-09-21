<div align="center">
  <h2>Otukai</h2>
  <p>1.21.10 Paper/Spigot plugin</p>
</div>

<br>

### 概要
サバイバルをテーマに制限時間内に様々なタスクをクリアしていくミニゲームです。  プレイ人数に応じて、変数が乗算されていく仕組みなので、人数に合わせた難易度になります。 全体の難易度としては、**初心者～中級者** 向けに設定しており、[`TaskList.kt`](https://github.com/Sakamochanq/otukai/blob/master/src/main/kotlin/com/Sakamochanq/otukai/task/TaskList.kt)を編集することで、自分なりのタスクを作成することが出来ます。普通のサバイバルに少し飽きたので作ってみました。多少の競技性をもたせるために、スコア/ベストスコア システムを設けてみました。マルチプレイの場合は、協力プレイになります。対戦モードなどは実装していません。ソースコードとDockerfileを公開するので、自由に作成/コンパイルしてみて下さい。

<br>

<div>
  <table>
    <tbody>
      <tr>
        <td>コマンド</td>
        <td>内容</td>
      </tr>
      <tr>
        <td>/otukai start</td>
        <td>ゲーム開始</td>
      </tr>
      <tr>
        <td>/otukai stop</td>
        <td>ゲーム終了</td>
      </tr>
    </tbody>
  </table>
</div>

<br>
<br>

### プレイ画像

<div>
  <table>
    <thead>
      <tr>
        <th>Fig.1</th>
        <th>Fig.2</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th>
            <img src="./.github/screenie/1.png" width="400"/>
        </th>
        <th>
            <img src="./.github/screenie/2.png" width="400"/>
        </th>
      </tr>
      <tr>
        <td>
          <img src="./.github/screenie/3.png" width="400"/>
        </td>
        <td>
          <img src="./.github/screenie/4.png" width="400"/>
        </td>
      </tr>
    </tbody>
  </table>
</div>

<br>
<br>

### 動作環境

Minecraft 1.21.10 Paper/Spigot

<br>
<br>

### 環境構築

1. Dockerをインストールする.

<br>

2. Docker imageのビルド.

    ```bash
    docker build -t otukai .
    ```

<br>

3. プロジェクトのビルド

    ```bash
    ./gradlew build
    ```

<br>

4. Dockerサーバーの起動

    ```bash
    docker compose start minecraft
    ```

<br>

5. Minecraftクライアントを起動し、`localhost` に接続する。

<br>
<br>

### 追記

コーディング補助として **Github Copilot** を使用しています。  
Kotlinは良く分かってないので、コードの品質は保証できません。


Release under the MIT License.


<br>
<br>

### 著者

Sakamochanq

<br>
<br>
